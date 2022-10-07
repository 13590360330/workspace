package com.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 身份证工具-IdcardUtil
 */
public class IdcardLcUtils {

    public static void main(String[] args) {

        String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";

        //是否有效
        boolean valid = IdcardUtil.isValidCard( ID_18 );
        boolean valid15 = IdcardUtil.isValidCard( ID_15 );

        //转换
        //convert15To18 身份证15位转18位
        //getAgeByIdCard 获取年龄
        //getBirthByIdCard 获取生日
        //getYearByIdCard 获取生日年
        //getMonthByIdCard 获取生日月
        //getDayByIdCard 获取生日天
        //getGenderByIdCard 获取性别
        //getProvinceByIdCard 获取省份
        String convert15To18 = IdcardUtil.convert15To18( ID_15 );
        System.out.println(convert15To18);

        //年龄
        DateTime date = DateUtil.parse( "2017-04-10" );

        //getAgeByIdCard 获取年龄
        int age = IdcardUtil.getAgeByIdCard( ID_18, date );
        System.out.println(age);

        int age2 = IdcardUtil.getAgeByIdCard( ID_15, date );
        System.out.println(age2);

        //getBirthByIdCard 获取生日
        String birth = IdcardUtil.getBirthByIdCard( ID_18 );
        System.out.println(birth);

        String birth2 = IdcardUtil.getBirthByIdCard( ID_15 );
        System.out.println(birth2);

        //省份
        String province = IdcardUtil.getProvinceByIdCard( ID_18 );
        System.out.println(province);

        String province2 = IdcardUtil.getProvinceByIdCard( ID_15 );
        System.out.println(province2);

    }
}
