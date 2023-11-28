#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @descript : 日志工具类
# @Auth : miaowei
# @Time : 20/2/2023 下午7:50
import time


class Log(object):
    _info = "INFO"
    _warn = "WARN"
    _debug = "DEBUG"
    _dateformat = "%Y-%m-%d %H:%M:%S"

    def __init__(self, *args):
        if len(args) > 0:
            dateformat = args[0]
            self._dateformat = dateformat

    def info(self, msg):
        cur_date = time.strftime(self._dateformat, time.localtime(time.time()))
        out = f"""{cur_date} {self._info}: {msg}"""
        print(out)

    def warn(self, msg):
        cur_date = time.strftime(self._dateformat, time.localtime(time.time()))
        out = f"""{cur_date} {self._warn}: {msg}"""
        print(out)

    def debug(self, msg):
        cur_date = time.strftime(self._dateformat, time.localtime(time.time()))
        out = f"""{cur_date} {self._debug}: {msg}"""
        print(out)

