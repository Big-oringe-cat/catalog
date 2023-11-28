#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : postgres操作工具类
# @Auth : miaowei
# @Time : 20/2/2023 下午7:49
import psycopg2


class PgConn(object):
    _conn = None

    # 数据库连接初始化
    def __init__(self, database, user, password, host, port):
        self._conn = psycopg2.connect(database=database,
                                      user=user,
                                      password=password,
                                      host=host,
                                      port=port)

    def __del__(self):
        self.close()

    # 关闭连接
    def close(self):
        if self._conn is not None:
            self._conn.close()

    # 查询
    def select(self, sql):
        try:
            cur = self._conn.cursor()
            cur.execute(sql)
            results = cur.fetchall()
            cur.close()
            return results
        except Exception as e:
            print(e)
            print("warning: 查询失败" + sql)
            return ""

    # 清空表
    def truncate(self, tablename):
        try:
            cur = self._conn.cursor()
            cur.execute("TRUNCATE {} RESTART IDENTITY;".format(tablename))
            cur.close()
        except:
            print("warning: {}清空失败".format(tablename))

    # 单条插入
    def insert(self, sql):
        try:
            cur = self._conn.cursor()
            cur.execute(sql)
            self._conn.commit()
            results = cur.fetchall()
            cur.close()
            return results
        except:
            print("warning: 插入失败")
            return 0

    # 批量插入，data要求为数组
    def batch_insert(self, sql, data):
        try:
            cur = self._conn.cursor()
            cur.executemany(sql, data)
            self._conn.commit()
            cur.close()
        except Exception:
            print("warning: 批量插入失败")
            raise


class _PGExceptin(Exception):
    "this is pgconn's Exception"

    def __init__(self, msg):
        self.msg = msg

    def __str__(self):
        print(self.msg)
