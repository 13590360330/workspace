package com.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;

import java.nio.charset.Charset;


/**
 * author:liuch
 * date：2022-10-02
 * desc: XML工具-XmlUtil
 */
public class XmlUtilLcUtils {

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource( "111.xml" );
        Document document = XmlUtil.readXML( resource.getFile() );

        String s = FileUtil.readString( resource.getFile().getPath(), Charset.forName( "UTF-8" ) );

        //可以將xml轉爲map， bean , 也可以將beam或map转为xml
        System.out.println( XmlUtil.xmlToMap( s ) );


        System.out.println();
    }

}
