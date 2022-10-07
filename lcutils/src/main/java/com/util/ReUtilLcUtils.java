package com.util;

import cn.hutool.core.util.ReUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 正则工具-ReUtil
 */
public class ReUtilLcUtils {

    public static void main(String[] args) {
        String content = "ZZZaaabbbccc中文1234";
        //1,抽取多个分组然后把它们拼接起来
        String resultExtractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", content, "$1-$2");
        System.out.println(resultExtractMulti);  //Z-a

        //删除第一个匹配到的内容
        String resultDelFirst = ReUtil.delFirst("(\\w)aa(\\w)", content);
        System.out.println(resultDelFirst);  //ZZbbbccc中文1234

        //查找所有匹配文本
        List<String> resultFindAll = ReUtil.findAll("\\w{2}", content, 0, new ArrayList<String>());
        System.out.println(resultFindAll);  // 结果：["ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34"]

        //找到匹配的第一个数字
        Integer resultGetFirstNumber = ReUtil.getFirstNumber(content);
        System.out.println(resultGetFirstNumber);  // 结果：1234

        //给定字符串是否匹配给定正则
        boolean isMatch = ReUtil.isMatch("\\w+[\u4E00-\u9FFF]+\\d+", content);
        System.out.println(isMatch); //true

        //通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串
        //此处把1234替换为 ->1234<-
        String replaceAll = ReUtil.replaceAll(content, "(\\d+)", "->$1<-");
        System.out.println(replaceAll); //ZZZaaabbbccc中文->1234<-

        //将字符串中的所有特殊字符转义成字符
        //官方：转义给定字符串，为正则相关的特殊符号转义
        String escape = ReUtil.escape("我有个$符号{}");
        System.out.println(escape); //我有个\\$符号\\{\\}

    }

    /**
     * 获得匹配的字符串
     *
     * @param pattern 编译后的正则模式
     * @param content 被匹配的内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String get(Pattern pattern, String content, int groupIndex) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * 获得匹配的字符串
     *
     * @param regex 匹配的正则
     * @param content 被匹配的内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String get(String regex, String content, int groupIndex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return get(pattern, content, groupIndex);
    }

}
