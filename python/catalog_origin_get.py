#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : 获取元数据
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

def main():
    dateformat = "%Y-%m-%d %H:%M:%S"
    cur_date = time.strftime(dateformat, time.localtime(time.time()))
    # 获取当前pg集群中所有库名
    conn = PgConn(database="",
                  user=cfp.get("etl", "src_user"),
                  password=cfp.get("etl", "src_password"),
                  host=cfp.get("etl", "src_host"),
                  port=cfp.get("etl", "src_port"))
    sql_get_databases = """
    select datname from pg_database;
    """
    result1 = conn.select(sql_get_databases)
    conn.close()

    # 获取今日schema, table_name,  rows,size, business_term,id,type
    sql_get_row = f"""
    select schemaname , tablename, 
    cast(reltuples as int4) as row,
    round(cast(relpages/128.0 as numeric),2) as size, 
    cast(d.description as TEXT), c.oid,'table' from pg_class c
    left join pg_namespace n on c.relnamespace = n.oid
    left join pg_tables t on (n.nspname = t.schemaname  and c.relname = t.tablename)
    left join pg_description d on d.objoid=c.oid and d.objsubid = 0
    where c.relkind='r' and 
    schemaname not in ('information_schema','pg_catalog')
	UNION
	select v.schemaname, v.matviewname,0,0.0,v.definition,c.oid,'matview'
    from pg_class c
    left join pg_namespace n on c.relnamespace = n.oid
    left join pg_matviews v on (n.nspname = v.schemaname  and c.relname = v.matviewname)
    where  
    schemaname not in ('information_schema','pg_catalog')
	UNION
	select v.schemaname, v.viewname,0,0.0,v.definition,c.oid,'view'
    from pg_class c
    left join pg_namespace n on c.relnamespace = n.oid
    left join pg_views v on (n.nspname = v.schemaname  and c.relname = v.viewname)
    where  
    schemaname not in ('information_schema','pg_catalog')
	;
    """

    # 直接获取row,size总量
    values = []
    values_oid = []
    values_oid_dir = {}
    demo_dir = {}
    datasource = "PG"
    frequency = "1h"
    for i in result1:
        if len(i) > 0:
            database = i[0]
            try:
                # 对每个库进行统计
                conn2 = PgConn(database=database,
                               user=cfp.get("etl", "src_user"),
                               password=cfp.get("etl", "src_password"),
                               host=cfp.get("etl", "src_host"),
                               port=cfp.get("etl", "src_port"))
                result2 = conn2.select(sql_get_row)
                if len(result2) > 0:
                    print("info: 查询 {} 库成功".format(database))
                else:
                    print("info: 查询 {} 库无数据".format(database))
                for i in result2:
                    # ('ods', 'test_1', 0.0, 0)
                    # values.append(['postgre',dname,i[1],i[2],i[3],i[0],cur_date])
                    schema, table_name, rows, size, business_term, oid, type = i[0], i[1], i[2], i[3], i[4], i[5], i[6]
                    id = hashlib.md5((datasource + database + schema + table_name).encode()).hexdigest()
                    # id(md5) datasource database schema table_name business_term frequency rows size, cur_date, skip,expire
                    values.append(
                        [id, datasource, database, schema, table_name, business_term, frequency,
                         rows, size, type, cur_date, False, False]
                    )
                    if values_oid_dir.get(database) is None:
                        values_oid_dir[database] = []
                        values_oid_dir[database].append([oid, id, schema, table_name])
                    else:
                        values_oid_dir[database].append([oid, id, schema, table_name])
                    values_oid.append([oid, id, database])
                    sql_get_demo = f"""select * from "{schema}"."{table_name}" limit 3;"""
                    result_demo = conn2.select(sql_get_demo)
                    # 最多写入三行数据
                    count = 3
                    max_demo = len(result_demo)
                    start_index = 0
                    demo_dir[id] = []
                    while max_demo > 0 and count > 0:
                        demo_dir[id].append(result_demo[start_index])
                        start_index += 1
                        count -= 1
                        max_demo -= 1
            except Exception as e:
                print(str(e))
                # 有些库无法访问，跳过
                logger.warn("{} 库无法连接，跳过".format(database))
                continue
            finally:
                conn2.close()

    # 写入目标数据库
    if len(values) > 0:
        conn3 = PgConn(database=cfp.get("catalog", "pg_database"),
                       user=cfp.get("catalog", "pg_user"),
                       password=cfp.get("catalog", "pg_password"),
                       host=cfp.get("catalog", "pg_host"),
                       port=cfp.get("catalog", "pg_port"))
        # 清空table_origin
        conn3.truncate("table_origin")
        insert_table_origin = """
        insert into table_origin (id, datasource, database, schema, tablename, business_term, frequency,
         rows, size,type, update_time,skip,expire)
        values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);
        """
        conn3.batch_insert(insert_table_origin, values)
        logger.info("写入table_origin表成功")
        values_field = []
        for database, values_oid_list in values_oid_dir.items():
            conn4 = PgConn(database=database,
                           user=cfp.get("etl", "src_user"),
                           password=cfp.get("etl", "src_password"),
                           host=cfp.get("etl", "src_host"),
                           port=cfp.get("etl", "src_port"))
            for i in values_oid_list:
                table_oid = i[0]
                table_id = i[1]
                table_schema = i[2]
                table_name = i[3]
                sql_get_field = f"""
                select a.attname as field,t.typname as fieldtype,a.attnum,
                case when attlen>0 
                then attlen 
                else 
                case when a.atttypmod>0
                then a.atttypmod-4
                else 0
                end 
                end field_length,
                d.description as business_term
                from pg_attribute a 
                left join pg_type t on a.atttypid=t.oid
                left join pg_description d on d.objsubid=a.attnum and d.objoid=a.attrelid
                where a.attrelid={table_oid} and a.attnum>0 and a.attname not like '%pg.dropped%' 
                order by a.attnum;
                """
                result3 = conn4.select(sql_get_field)
                demo_list = demo_dir.get(table_id)
                index = 0
                for i in result3:
                    # table_id | field | fieldtype | field_length | field_demo | business_term | id
                    field = i[0]
                    fieldtype = i[1]
                    field_length = i[3]
                    field_demo, field_demo2, field_demo3 = "", "", ""
                    if demo_list is None:
                        field_demo = None
                    else:
                        if len(demo_dir[table_id]) >= 1:
                            field_demo = str(demo_dir[table_id][0][index])
                        if len(demo_dir[table_id]) >= 2:
                            field_demo2 = str(demo_dir[table_id][1][index])
                        if len(demo_dir[table_id]) >= 3:
                            field_demo3 = str(demo_dir[table_id][2][index])
                    business_term = i[4]
                    id = hashlib.md5((table_id + field).encode()).hexdigest()
                    values_field.append(
                        [table_id, field, fieldtype, field_length, field_demo, field_demo2, field_demo3, business_term,
                         id, cur_date])
                    index += 1
            conn4.close()
        # 清空field_origin
        conn3.truncate("field_origin", )
        insert_field_origin = """
        insert into field_origin (
        table_id,field,fieldtype,field_length,field_demo,field_demo2,field_demo3,business_term,
        id, update_time)
        values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);
        """
        conn3.batch_insert(insert_field_origin, values_field)
        logger.info("写入field_origin表成功")
        conn3.close()


if __name__ == "__main__":
    logger.info("origin get start")
    main()
    logger.info("origin get end")
