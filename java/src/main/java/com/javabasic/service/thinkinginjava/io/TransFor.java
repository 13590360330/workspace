package com.javabasic.service.thinkinginjava.io;


import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TODO [TCP&UDP Client端]
 */
public class TransFor {
    private static final int BSIZE = 1024;
    private static int TCPServercount = 1;

    private class TCPClient {
        private String IPADDRESS = "localhost";
        private int PORT = 9000;

        private TCPClient(String IPADDRESS, int PORT) {
            this.IPADDRESS = IPADDRESS;
            this.PORT = PORT;
        }

        void send(String sendpath) throws IOException {
            //创建输出流，将创建的输出流和客户端绑定
            //创建数据传输包，并将之与输出流绑定
            try (Socket client = new Socket( IPADDRESS, PORT );
                 DataOutputStream out = new DataOutputStream( client.getOutputStream() )) {
                System.out.println( "连接到主机：" + IPADDRESS + "，端口号：" + PORT );
                //抛出异常：UnknownHostException、IOException；
                System.out.println( "远程主机地址：" + client.getRemoteSocketAddress() );

                String s = FileUtils.readFileToString( new File( sendpath ), "utf-8" );
                System.out.println( s );
                //向数据传输包中写入数据(输出数据)
                out.writeUTF( s );

                //创建输入流，接收来自服务器端的数据
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream( inFromServer );
                System.out.println( "服务器响应：" + in.readUTF() );
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public void getTCPClient(String IPADDRESS, int PORT, String pathname) {
        TCPClient tcpClient = new TCPClient( IPADDRESS, PORT );
        try {
            tcpClient.send( pathname );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Logs.getLogs( "TransFor" );
        TransFor transFor = new TransFor();
//        transFor.getTCPClient( args[1], new Integer( args[2] ), "E:\\日常文件\\临时目录\\新建文件夹\\1.txt" );
        transFor.getTCPClient( "127.0.0.1", new Integer( "9000" ), "E:\\日常文件\\临时目录\\新建文件夹\\1.txt" );
    }
}