package com.javabasic.dao;

import java.io.*;

public class ExcuteShell2 {

    /**
     * ִ��jar����ȡ����ֵ
     *
     * @return������ֵ
     */
    public static String encryption(String tablename){
        StringBuilder line=new StringBuilder();
        String mm=null;
        ProcessBuilder proc =
                new ProcessBuilder("/usr/java/jdk1.8.0_181/bin/java.exe","-version");
//        ProcessBuilder proc =
//                new ProcessBuilder("D:\\BIGDATA\\software\\jdk1.8.0_121\\bin\\java.exe","-version");
        Process ps = null;
        try {
            ps = proc.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = ps.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String nn=null;
        if(proc==null){
            tablename="1";
        }else {
            tablename="2";
        }
        while (true) {
            try {
                if (!((nn=br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            line.append(nn+",");
        }
        mm=line.toString() + tablename;
        return mm;
    }


    public static void main(String[] args){
        ExcuteShell2 excuteShell2=new ExcuteShell2();
        String line=excuteShell2.encryption("aaa");
        System.out.println(line);
    }
}
