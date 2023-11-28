#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : neo4j操作工具类
# @Auth : miaowei
# @Time : 20/2/2023 下午8:41
from py2neo import Node, Relationship, Graph, NodeMatcher, RelationshipMatcher


class NeoConn(object):
    _graph = None
    _matcher = None

    # 初始化连接
    def __init__(self, url, user, passwd):
        self._graph = Graph(url, auth=(user, passwd))
        self._matcher = NodeMatcher(self._graph)

    def __del__(self):
        self.close()

    def get_conn(self):
        return self._graph

    def get_matcher(self):
        return self._matcher

    # 匹配1node
    def match_one(self, table_label, properties):
        return self._matcher.match(table_label).where(str(properties)).first()

    # 匹配node返回列表
    def match_list(self, table_label, properties):
        return list(self._matcher.match(table_label).where(str(properties)))

    # 以主键进行upsert
    def merge(self, table_label, prime_key, properties):
        node = Node(table_label, **properties)
        self._graph.merge(node, table_label, prime_key)

    def create_relationship(self, node1, node2, r_label, prime_key, r_properties,):
        if node1 is None or node2 is None:
            print("node cant be null,skip")
        r = Relationship(node1, r_label, node2, **r_properties)
        self._graph.merge(r, r_label, prime_key)

    # 销毁方法
    def close(self):
        if self._graph is not None:
            self._graph = None


