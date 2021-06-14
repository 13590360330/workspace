package com.gupaoedu.vip.netty.rpc.registry;

import com.gupaoedu.vip.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //用保存所有可用的服务
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String, Object>();

    //保存所有相关的服务类
    private List<String> classNames = new ArrayList<String>();

    public RegistryHandler() {
        //完成递归扫描
        scannerClass( "com.gupaoedu.vip.netty.rpc.provider" );
        doRegister();
    }

    /**
     * ChannelInboundHandlerAdapter 的channelRead()方法会将客户端发送的数据包装成msg,然后进行读取
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();

        //1,读取客户端发送过来的数据包
        InvokerProtocol request = (InvokerProtocol) msg;

        //2,进行业务逻辑的处理
        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
        //使用反射调用
        if (registryMap.containsKey( request.getClassName() )) {
            Object clazz = registryMap.get( request.getClassName() );
            Method method = clazz.getClass().getMethod( request.getMethodName(), request.getParames() );
            result = method.invoke( clazz, request.getValues() );
        }

        //3,发送数据
        ctx.write( result );
        ctx.flush();
        ctx.close();
        //等同于
        //ctx.writeAndFlush( request );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    /*
     * 递归扫描
     */
    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource( packageName.replaceAll( "\\.", "/" ) );
        File dir = new File( url.getFile() );
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if (file.isDirectory()) {
                scannerClass( packageName + "." + file.getName() );
            } else {
                classNames.add( packageName + "." + file.getName().replace( ".class", "" ).trim() );
            }
        }
    }

    /**
     * 完成注册
     */
    private void doRegister() {
        if (classNames.size() == 0) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName( className );
                Class<?> i = clazz.getInterfaces()[0];
                registryMap.put( i.getName(), clazz.newInstance() );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
