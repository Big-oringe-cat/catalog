#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : 采集存储过程，视图，物化视图
# @Auth : miaowei
# @Time : 29/04/2023 下午2:30
import os
import time
import configparser
import hashlib
from util.pg_conn import PgConn
from util.log import Log
conf_file = "/vgc_data_lake/catalog/etl/conf/application.conf"
# conf_file = "E:/idea_workspace/sirun/conf/application.conf"
save_path = "/vgc_data_lake/catalog/get_sql/"
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

    sql_get_row = f"""
SELECT
	nspname AS "schemaname",
	proname AS "name",
	pg_catalog.pg_get_functiondef ( P.oid ) AS "sql",
	'proc' AS "type" 
FROM
	pg_namespace n
	JOIN pg_proc P ON pronamespace = n.oid 
WHERE
	nspname NOT IN ( 'pg_catalog', 'information_schema' ) UNION
SELECT
	v.schemaname AS "schemaname",
	v.matviewname AS "name",
	pg_catalog.pg_get_viewdef ( C.oid, TRUE ) AS "sql",
	'matview' AS "type" 
FROM
	pg_class
	C LEFT JOIN pg_namespace n ON C.relnamespace = n.oid
	LEFT JOIN pg_matviews v ON ( n.nspname = v.schemaname AND C.relname = v.matviewname ) 
WHERE
	schemaname NOT IN ( 'information_schema', 'pg_catalog' ) UNION
SELECT
	v.schemaname AS "schemaname",
	v.viewname AS "name",
	pg_catalog.pg_get_viewdef ( C.oid, TRUE ) AS "sql",
	'view' AS "type" 
FROM
	pg_class
	C LEFT JOIN pg_namespace n ON C.relnamespace = n.oid
	LEFT JOIN pg_views v ON ( n.nspname = v.schemaname AND C.relname = v.viewname ) 
WHERE
	schemaname NOT IN ( 'information_schema', 'pg_catalog' );
    """
    # 刷新系统表
    sql_analyze_verbose = """analyze verbose;"""

    # 确保保存路径存在
    if not os.path.exists(save_path):
        os.mkdir(save_path)

    # 获取sql文件
    for i in result1:
        if len(i) > 0:
            database = i[0]
            try:
                host = cfp.get("etl", "src_host")
                port = cfp.get("etl", "src_port")
                # 对每个库进行统计
                conn2 = PgConn(database=database,
                               user=cfp.get("etl", "src_user"),
                               password=cfp.get("etl", "src_password"),
                               host=cfp.get("etl", "src_host"),
                               port=cfp.get("etl", "src_port"))
                # 刷新系统表，确保之后获取的表结构信息为最新
                # conn2.select(sql_analyze_verbose)
                # 获取每个库中的所有视图，物化视图，存储过程的ddl
                result2 = conn2.select(sql_get_row)
                if len(result2) > 0:
                    print("info: 查询 {} 库成功".format(database))
                else:
                    print("info: 查询 {} 库无数据".format(database))
                for i in result2:
                    schemaname = i[0]
                    name = i[1]
                    sql = i[2]
                    type = i[3]
                    # sql为文件内容写入文件
                    filename = "{}-{}-{}-{}-{}-{}.sql".format(host, port, database, schemaname, name, type)

                    with open(save_path + filename, "w", encoding="utf-8") as f:
                        f.write(sql)

                    logger.info("写入文件{}".format(filename))
            except Exception as e:
                print(str(e))
                # 有些库无法访问，跳过
                logger.warn("{} 库无法连接，跳过".format(database))
                continue
            finally:
                conn2.close()


if __name__ == "__main__":
    main()
