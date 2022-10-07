package com.util;

import cn.hutool.core.util.DesensitizedUtil;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 信息脱敏工具-DesensitizedUtil
 */
public class DesensitizedLcUtils {

    public static void main(String[] args) {
        // 5***************1X
        System.out.println(DesensitizedUtil.idCardNum( "51343620000320711X", 1, 2 ));
        System.out.println(DesensitizedUtil.mobilePhone("18049531999"));
        System.out.println(DesensitizedUtil.password("1234567890"));
    }

}
