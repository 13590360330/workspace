package com.util;

import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;

public class HttpLcUtils {


    public static void main(String[] args) {
        String result1= HttpUtil.get("https://fonts.googleapis.com/css?family=Roboto+Mono|Source+Sans+Pro:300,400,600");

        System.out.println(result1);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------");


        String result2 = HttpRequest.post("https://fonts.googleapis.com/css?family=Roboto+Mono|Source+Sans+Pro:300,400,600")
                .header( Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .form(new HashMap<>(  ) )//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        Console.log(result2);
    }
}
