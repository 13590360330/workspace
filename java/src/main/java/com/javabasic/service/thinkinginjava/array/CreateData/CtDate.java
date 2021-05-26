package com.javabasic.service.thinkinginjava.array.CreateData;

import com.javabasic.dao.Generator;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CtDate {

    //User
    private ArrayList<String> data;

    public CtDate(Class<? extends Generator>... aClass) {
        this.data = ctdate( aClass );
    }

    private ArrayList<String> ctdate(Class[] aClass) {
        ArrayList<String> adate = new ArrayList<String>();
        for (Class a : aClass) {
            Object b = null;
            try {
                b = a.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (b instanceof Generator)
                adate.add( ((Generator) b).next().toString() );
        }
        return adate;
    }

    public ArrayList<String> getDate() {
        return data;
    }

    public String getDateStr() {
        //结束日期
        Date date = new Date();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "YYYYMMdd" );
        String format = simpleDateFormat1.format( date );

        //起始日期
        Random r = new Random();
        int mod = 1000000000;
        Date date2 = new Date();     //获取当前时间
        long msec = date2.getTime();
        long ms = msec - (long) r.nextInt( mod ) * 500;
        date.setTime( new Long( ms ) );
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat( "YYYYMMdd" );
        String format2 = simpleDateFormat1.format( date );

        String str = format2 + "\001" + StringUtils.join( data, "\001" ) + "\001" + format;
        return str;
    }
}
