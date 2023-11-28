#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript :
# @Auth : miaowei
# @Time : 20/2/2023 下午7:47
from util.log import Log
from util.pg_conn import PgConn
from util.neo_conn import NeoConn
import configparser
from util.log import Log

logger = Log()
conf_file = "/vgc_data_lake/catalog/etl/conf/application.conf"
# conf_file = "E:/idea_workspace/sirun/conf/application.conf"
cfp = configparser.ConfigParser()
cfp.read(conf_file, encoding='utf-8')

table_label = "TABLE"
table_relationship_label = "TABLE_RELATION"
field_label = "FIELD"
field_relationship_label = "FIELD_RELATION"


def table_relation(conn, graph):
    # get table name from pg
    sql_get_tables = """
    select 
	md5(cast(a.tid||a.import_tid||a.datasource||a.database||a.schema||a.table as varchar)),
	a.tid,a.import_tid,a.datasource,a.database,a.schema,a.table from (
	select	
    coalesce(src_tid,'') as tid,coalesce(src_import_tid,'') as import_tid,coalesce(src_datasource,'') as datasource,
	coalesce(src_database,'') as database,coalesce(src_schema,'') as schema,coalesce(src_table,'') as table 
	from relation_check 
	group by src_tid,src_import_tid,src_datasource,src_database,src_schema,src_table 
    union 
    select 
    coalesce(dst_tid,'') as tid,coalesce(dst_import_tid,'') as import_tid,coalesce(dst_datasource,'') as datasource,
	coalesce(dst_database,'') as database,coalesce(dst_schema,'') as schema,coalesce(dst_table,'') as table 
	from relation_check  
	group by dst_tid,dst_import_tid,dst_datasource,dst_database,dst_schema,dst_table
	) a;
    """
    result_table = conn.select(sql_get_tables)
    # import table node to neo4j
    for i in result_table:
        md5 = i[0]
        tid = i[1]
        import_tid = i[2]
        datasource = i[3]
        database = i[4]
        schema = i[5]
        table = i[6]
        prime_key = "md5"
        properties = {prime_key: md5, "name": table,"tid": tid, "import_tid": import_tid,
                      "datasource": datasource, "database": database,
                      "schema": schema, "table": table}
        graph.merge(table_label, prime_key, properties)
    logger.info("neo table node merged")
    # get table relationship from pg
    sql_get_table_join = """
    select 
	md5(cast(coalesce(src_tid,'') || coalesce(src_import_tid,'') || coalesce(src_datasource,'') ||
	coalesce(src_database,'') || coalesce(src_schema,'') || coalesce(src_table,'') as varchar)) as src_md5,
	md5(cast(coalesce(dst_tid,'') || coalesce(dst_import_tid,'') || coalesce(dst_datasource,'') ||
	coalesce(dst_database,'') || coalesce(dst_schema,'') || coalesce(dst_table,'') as varchar)) as dst_md5,
	coalesce(relation,''), id  from relation_check 
	group by src_tid,src_import_tid,src_datasource,src_database,src_schema,src_table,
	dst_tid,dst_import_tid,dst_datasource,dst_database,dst_schema,dst_table,relation,id
	;
    """
    result_table_relation = conn.select(sql_get_table_join)
    for r in result_table_relation:
        src_md5, dst_md5, realtion, id = r[0], r[1], r[2], r[3]
        src_where = f""" _.md5 = '{src_md5}'"""
        src_nodes = graph.match_one(table_label, src_where)
        dst_where = f""" _.md5 = '{dst_md5}'"""
        dst_nodes = graph.match_one(table_label, dst_where)
        prime_key = "join"
        properties = {prime_key: realtion, "id": id}
        graph.create_relationship(src_nodes, dst_nodes, table_relationship_label, prime_key, properties)
    logger.info("neo table relationship merged")


def field_relation(conn, graph):
    # get field name from pg
    sql_get_field = """
    select 
	md5(cast(a.tid||a.import_tid||a.fid||a.import_fid||a.datasource||a.database||a.schema||a.table||a.field  as varchar)),
	a.tid,a.import_tid,a.fid,a.import_fid,a.datasource,a.database,a.schema,a.table,a.field from (
	select	
    coalesce(src_tid,'') as tid,coalesce(src_import_tid,'') as import_tid,
    coalesce(src_fid,'') as fid,coalesce(src_import_fid,'') as import_fid,
	coalesce(src_datasource,'') as datasource,coalesce(src_database,'') as database,coalesce(src_schema,'') as schema,
	coalesce(src_table,'') as table,coalesce(src_field,'') as field
	from relation_check 
	group by src_tid,src_import_tid,src_fid,src_import_fid,src_datasource,src_database,src_schema,src_table,src_field 
    union 
    select 
    coalesce(dst_tid,'') as tid,coalesce(dst_import_tid,'') as import_tid,
    coalesce(dst_fid,'') as fid,coalesce(dst_import_fid,'') as import_fid,
	coalesce(dst_datasource,'') as datasource,coalesce(dst_database,'') as database,coalesce(dst_schema,'') as schema,
	coalesce(dst_table,'') as table,coalesce(dst_field,'') as field  	
	from relation_check 
	group by dst_tid,dst_import_tid,dst_fid,dst_import_fid,dst_datasource,dst_database,dst_schema,dst_table,dst_field
	) a;
    """
    result_field = conn.select(sql_get_field)
    # import field node to neo4j
    for i in result_field:
        md5 = i[0]
        tid = i[1]
        import_tid = i[2]
        fid = i[3]
        import_fid = i[4]
        datasource = i[5]
        database = i[6]
        schema = i[7]
        table = i[8]
        field = i[9]
        prime_key = "md5"
        properties = {prime_key: md5, "name": table+"."+field, "tid": tid, "import_tid": import_tid,
                      "fid": fid, "import_fid": import_fid, "datasource": datasource,
                      "database": database, "schema": schema, "table": table, "field": field}
        graph.merge(field_label, prime_key, properties)
    logger.info("neo field node merged")

    # get field relationship from pg
    sql_get_field_join = """
	select 
	md5(cast(coalesce(src_tid,'') || coalesce(src_import_tid,'') || coalesce(src_fid,'') || coalesce(src_import_fid,'') || 
	coalesce(src_datasource,'') || coalesce(src_database,'') || coalesce(src_schema,'') || coalesce(src_table,'') ||
	coalesce(src_field,'') as varchar)) as src_md5,
	md5(cast(coalesce(dst_tid,'') || coalesce(dst_import_tid,'') || coalesce(dst_fid,'') || coalesce(dst_import_fid,'') || 
	coalesce(dst_datasource,'') || coalesce(dst_database,'') || coalesce(dst_schema,'') || coalesce(dst_table,'') || 
	coalesce(dst_field,'') as varchar)) as dst_md5,
	coalesce(relation,''), id  from relation_check  
	group by src_tid,src_import_tid,src_fid,src_import_fid,src_datasource,src_database,src_schema,src_table,src_field,
	dst_tid,dst_import_tid,dst_fid,dst_import_fid,dst_datasource,dst_database,dst_schema,dst_table,dst_field,relation,id
	;
    """
    result_field_relation = conn.select(sql_get_field_join)
    for r in result_field_relation:
        src_md5, dst_md5, realtion, id = r[0], r[1], r[2], r[3]
        src_where = f""" _.md5 = '{src_md5}'"""
        src_nodes = graph.match_one(field_label, src_where)
        dst_where = f""" _.md5 = '{dst_md5}'"""
        dst_nodes = graph.match_one(field_label, dst_where)
        prime_key = "join"
        properties = {prime_key: realtion, "id": id}
        graph.create_relationship(src_nodes, dst_nodes, field_relationship_label, prime_key, properties)
    logger.info("neo field relationship merged")


def main():
    graph = NeoConn(url=cfp.get("catalog", "neo_url"),
                    user=cfp.get("catalog", "neo_user"),
                    passwd=cfp.get("catalog", "neo_password")
                    )
    conn = PgConn(database=cfp.get("catalog", "pg_database"),
                  user=cfp.get("catalog", "pg_user"),
                  password=cfp.get("catalog", "pg_password"),
                  host=cfp.get("catalog", "pg_host"),
                  port=cfp.get("catalog", "pg_port")
                  )

    table_relation(conn, graph)

    field_relation(conn, graph)


if __name__ == "__main__":
    main()
