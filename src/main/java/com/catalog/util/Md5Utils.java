package com.catalog.util;

import org.springframework.util.DigestUtils;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: liuliqian
 * @since: 2019/8/15 下午9:01
 * @history: 1.2019/8/15 created by liuliqian
 */
public class Md5Utils {

    public static String generateMD5(String originStr){

        return new String(DigestUtils.md5DigestAsHex(originStr.getBytes()));
    }

}
