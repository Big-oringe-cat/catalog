#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : 元数据一致性校验，名字准确无误。     table，field,relation表的表名和table_origin,field_origin中的表名和字段名一致
# @Auth : miaowei
# @Time : 21/11/2022 下午3:30
import time
import configparser
import hashlib
from util.pg_conn import PgConn
from util.log import Log
conf_file = "/vgc_data_lake/catalog/etl/conf/application.conf"
# conf_file = "E:/idea_workspace/sirun/conf/application.conf"
cfp = configparser.ConfigParser()
cfp.read(conf_file, encoding='utf-8')
logger = Log()


# 表校验
def table_check(conn):
    sql_table_check = """
    select o.id as oid,d.id as import_id,
    coalesce(o.datasource,d.datasource,''),coalesce(o.database,d.database_name,''),
    coalesce(o.schema,d.schema_name,''),coalesce(o.tablename,d.tablename,''),o.business_term,o.frequency,o.rows,o.size,
    d.table_comment,d.table_comment_eng,
    case when o.tablename is null 
    then 1 else
    case when d.tablename is null 
    then 2 else 0 end
    end as origin_flag,o.type,d.update_time,o.update_time,d.category,d.update_frequency,d.operator
    from (select * from data_table where tablename is not null) d
    full outer join (select * from table_origin where tablename is not null and skip is False and expire is False) o
    on (lower(o.tablename)=lower(d.tablename) and lower(o.schema)=lower(d.schema_name) and lower(o.database)=lower(d.database_name) and o.datasource=d.datasource)
    ;
    """
    result1 = conn.select(sql_table_check)

    table_check_values = []

    for i in result1:
        i = list(i)
        if "." in i[5]:
            i[5] = i[5].split(".", 1)[1]
        i[2] = i[2].strip()
        i[3] = i[3].strip()
        i[4] = i[4].strip()
        i[5] = i[5].strip()
        table_check_values.append(i)

    insert_table_check="""
    insert into table_check (oid,import_id,datasource,database_name,schema_name,tablename,business_term,frequency,
    rows,size,table_comment,table_comment_eng,origin_flag,type,update_time,modify_time,category,update_frequency,operator)
    values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);
    """
    conn.truncate("table_check")
    conn.batch_insert(insert_table_check, table_check_values)
    logger.info("写入table_check表成功")


# 字段校验
def field_check(conn):
    sql_field_check = """
    select coalesce(d.field,f.field,''),f.fieldtype,f.field_length,f.field_demo,f.field_demo2,f.field_demo3,f.business_term,
    c.oid,c.import_id,f.id,d.id,c.datasource,c.database_name,c.schema_name,c.tablename,
    d.field_type,d.field_comment,d.field_comment_eng,
    case when c.origin_flag='0' and d.field is not null and f.field is not null then 0 
    else case when d.field is  null
    then 1 else 2
    end 
    end as origin_flag,
    d.update_time,f.update_time
    from data_field d
    left join table_check c
    on c.import_id=d.table_id
    left join field_origin f
    on c.oid=f.table_id and lower(d.field)=lower(f.field)
    union 
    select coalesce(f.field,d.field,''),f.fieldtype,f.field_length,f.field_demo,f.field_demo2,f.field_demo3,f.business_term,
    c.oid,c.import_id,f.id,d.id,c.datasource,c.database_name,c.schema_name,c.tablename,
    d.field_type,d.field_comment,d.field_comment_eng,
    case when c.origin_flag='0' and d.field is not null and f.field is not null then 0 
    else case when d.field is  null
    then 1 else 2
    end 
    end as origin_flag,
    d.update_time,f.update_time
    from field_origin f
    left join table_check c
    on c.oid=f.table_id
    left join data_field d
    on c.import_id=d.table_id and lower(d.field)=lower(f.field)
    ;	
    """
    result2 = conn.select(sql_field_check)

    field_check_values = []

    for i in result2:
        i = list(i)
        i[0] = i[0].strip()
        field_check_values.append(i)

    insert_field_check="""
    insert into field_check (
    field,fieldtype,field_length,field_demo,field_demo2,field_demo3,
    business_term,tid,import_tid,fid,import_fid,datasource,database_name,schema_name,tablename,
    import_type,field_comment,field_comment_eng,origin_flag,update_time,modify_time
    )
    values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);
    """
    conn.truncate("field_check")
    # for i in field_check_values:
    #     print(i)
    #     conn.batch_insert(insert_field_check, [i])
    conn.batch_insert(insert_field_check, field_check_values)
    logger.info("写入field_check表成功")


# 血缘校验
def relation_check(conn):
    sql_relation_check = """
    select 
    s.fid,s.import_fid,s.tid,s.import_tid,r.src_field,r.src_table,r.src_datasource,r.src_database,r.src_schema,
    d.fid,d.import_fid,d.tid,d.import_tid,r.dst_field,r.dst_table,r.dst_datasource,r.dst_database,r.dst_schema,
    case when s.field is null 
    then 1 else
    case when d.field is null 
    then 2 else 0
    end
    end as check_flag,
    r.realtion,r.id
    from (
    select id,realtion,
    trim(src_datasource) as src_datasource,trim(src_database) as src_database,trim(src_schema) as src_schema,
    trim(src_table) as src_table,trim(src_field) as src_field,
    trim(dst_datasource) as dst_datasource,trim(dst_database) as dst_database,trim(dst_schema) as dst_schema,
    trim(dst_table) as dst_table,trim(dst_field) as dst_field
    from data_relation) r
    left join field_check s
    on (lower(r.src_field) is not distinct from lower(s.field))=true 
    and (lower(r.src_table) is not distinct from lower(s.tablename))=true 
    and (r.src_datasource is not distinct from s.datasource)=true 
    and (lower(r.src_database) is not distinct from lower(s.database_name))=true 
    and (lower(r.src_schema) is not distinct from lower(s.schema_name))=true
    left join field_check d
    on (lower(r.dst_field) is not distinct from lower(d.field))=true 
    and (lower(r.dst_table) is not distinct from lower(d.tablename))=true 
    and (r.dst_datasource is not distinct from d.datasource)=true 
    and (lower(r.dst_database) is not distinct from lower(d.database_name))=true 
    and (lower(r.dst_schema) is not distinct from lower(d.schema_name))=true
    ;
    """
    result3 = conn.select(sql_relation_check)

    relation_check_values = []

    for i in result3:
        relation_check_values.append(i)

    insert_relation_check="""
    insert into relation_check (
    src_fid,src_import_fid,src_tid,src_import_tid,src_field,src_table,src_datasource,src_database,src_schema,
    dst_fid,dst_import_fid,dst_tid,dst_import_tid,dst_field,dst_table,dst_datasource,dst_database,dst_schema,
    check_flag,relation,id
    )
    values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);
    """
    conn.truncate("relation_check")
    conn.batch_insert(insert_relation_check, relation_check_values)
    logger.info("写入relation_check表成功")


# 全局索引
def global_index(conn):
    # sql_global_index = """
    # select coalesce(oid,import_id),tablename,'table',coalesce(tablename,'')||coalesce(business_term,'')||coalesce(table_comment,'')||coalesce(table_comment_eng,'') from table_check
    # union
    # select coalesce(fid,import_fid),field,'field',coalesce(field,'')||coalesce(business_term,'')||coalesce(field_comment,'')||coalesce(field_comment_eng,'') from field_check
    # union
    # select coalesce(id,''),src_table||':'||src_field||'->'||dst_table||':'||dst_field,'relation',relation from relation_check
    # ;
    # """
    sql_global_index = """
    select 
    import_id as id,
    tablename,
    'table',
    coalesce(tablename,'')||
	(case when type = 'view' or type = 'matview'
    then '' else coalesce(business_term,'')
    end)
	||coalesce(table_comment,'')||coalesce(table_comment_eng,'') as desc,
    NULL 
    from table_check
    where import_id is not null
    union
    select 
    import_fid as id,
    field,
    'field' as type,
    coalesce(field,'')||coalesce(business_term,'')||coalesce(field_comment,'')||coalesce(field_comment_eng,'') as desc,
    import_tid as tid
    from field_check
    where import_fid is not null and import_tid is not null
    ;
    """
    result4 = conn.select(sql_global_index)

    global_index_values = []

    for i in result4:
        global_index_values.append(i)

    insert_global_index="""
    insert into global_index (
    id,name,type,description,tid
    )
    values (%s,%s,%s,%s,%s);
    """
    conn.truncate("global_index")
    conn.batch_insert(insert_global_index, global_index_values)
    logger.info("写入global_index表成功")


def main():

    # 获取当前pg集群中所有库名
    conn = PgConn(database=cfp.get("catalog", "pg_database"),
                  user=cfp.get("catalog", "pg_user"),
                  password=cfp.get("catalog", "pg_password"),
                  host=cfp.get("catalog", "pg_host"),
                  port=cfp.get("catalog", "pg_port")
                  )
    table_check(conn)
    field_check(conn)
    relation_check(conn)
    global_index(conn)
    conn.close()


if __name__ == "__main__":
    logger.info("check start")
    main()
    logger.info("check end")
