package com.javabasic.service.thinkinginjava.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO [TCP&UDP  Server端,在运行TCP时一定要先开Server端,否则client端会报错]
 *
 * 执行命令
 * java -cp .:bigdata-1.0-SNAPSHOT.jar:lib/* com.java.com.service.Thinking_In_Java.io.TCPServer 9001
 */
public class TCPServer extends Thread {

    private static int TCPServercount = 1;
    private int PORT = 9000;
    private ServerSocket serverSocket;
    private final int no = TCPServercount++;
    private String getpath = null;

    /**
     * 在构造函数中创建ServerSocket对象；
     *
     * @throws IOException
     * @parameter port:端口号
     */
    TCPServer(int port, String getpath) throws IOException {
        this.PORT = port;
        this.getpath = getpath;
        //创建连接
        //抛出异常：IOException
        serverSocket = new ServerSocket( PORT );
        //设定连接超时
        serverSocket.setSoTimeout( 100000 );
    }

    /**
     * 线程的run函数
     */
    public void run() {
        /**
         * 创建Socket对象，通过Socket对象和客户端进行通信；
         * 数据的输入输出和客户端是一样的，利用OutputStream、
         * IntputStream、DataOutputStream、DataInputStream实现；
         */
        Socket socket = null;
        try {
            while (true) {
                System.out.println( "等待客户端的连接，端口号为：" + serverSocket.getLocalPort() );
                //创建Socket对象，并绑定server
                socket = serverSocket.accept();
                System.out.println( "远程客户端主机地址为：" + socket.getRemoteSocketAddress() );

                //创建输入流，读取客户端传过来的数据
                InputStream inputFromClient = socket.getInputStream();
                DataInputStream in = new DataInputStream( inputFromClient );
                System.out.println( "接收到的来自客户端[ TCPServer " + no + "]的信息" );
//                FileUtils.copyInputStreamToFile( in, new File( getpath ) );    卡住了??????
                String s = in.readUTF();
                FileUtils.writeStringToFile( new File( getpath ),s,"utf-8" );
                System.out.println("传输完成");

                //创建输出流，对客户端做出回复
                OutputStream outputFromServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream( outputFromServer );
                out.writeUTF( "服务器的响应：谢谢连接。" + serverSocket.getLocalPort() );
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TCPServer tcpServer = null;
        try {
//            tcpServer = new TCPServer( new Integer( args[0] ), "/home/ThinkingInJava/test/UsingBuffers.class" );
            tcpServer = new TCPServer( new Integer( "9000" ), "E:\\日常文件\\临时目录\\新建文件夹\\2.txt" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        tcpServer.run();
    }
}

