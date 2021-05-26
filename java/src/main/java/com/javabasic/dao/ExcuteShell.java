package com.javabasic.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class ExcuteShell {

    /**
     * ִ��jar����ȡ����ֵ
     *
     * @param params "dcsm_day_control"
     * @return������ֵ
     */
    public static final synchronized String encryption(String params) throws IOException {
        // ���ܺ�����ݶ���
        String encryptionData = "1";
        String jarpath="/home/gpdata/package/bigdata-1.0-SNAPSHOT-shaded.jar";

            // ��������
//            String encryption = "echo -E \"{0}\" | openssl aes-128-cbc -e -kfile {1} -base64";
            String encryption = "java -jar {0} {1}";

            // �滻������ռλ��
            encryption = MessageFormat.format(encryption, jarpath, params);

            String[] sh = new String[]{"/bin/sh", "-c", encryption};

            // Execute Shell Command
            ProcessBuilder pb = new ProcessBuilder(sh); //Process process = Runtime.getRuntime().exec(new String[]{jarpath,parameter1,parameter2});

            Process p = pb.start();

//            encryptionData="2";
            encryptionData = getShellOut(p);

        return encryptionData;
    }

    /**
     * ��Java�����в���Shell����������������ȡ��������
     * ��ȡ���������
     *
     * @param p ����
     * @return ��������ж�ȡ������
     * @throws IOException
     */
    public static final String getShellOut(Process p) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedInputStream in = null;
        BufferedReader br = null;

        try {

            in = new BufferedInputStream(p.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String s;

            while ((s = br.readLine()) != null) {
                // ׷�ӻ��з�
                sb.append(s);
//                sb.append("\n");
            }

        } catch (IOException e) {
            throw e;
        } finally {
            br.close();
            in.close();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String jarpath="/home/gpdata/package/bigdata-1.0-SNAPSHOT-shaded.jar";
        String params="dcsm_day_control";
        String result =ExcuteShell.encryption(params);
        System.out.println(result);
    }
}
