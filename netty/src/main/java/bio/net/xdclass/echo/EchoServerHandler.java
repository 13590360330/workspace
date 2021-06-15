package bio.net.xdclass.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * 测试telnet 127.0.0.1 8080  输入数据可查看执行过程
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //写数据,第一和第二种数据都会流经当前ChannelHandlerContext之后的所有handler,第三种只会从当前ChannelHandlerContext到下一个handler(见图ChannelHandlerContext.jpg)
        //第一种 (Unpooled.copiedBuffer(xxx,...) 申请一部分内存存储值xxx)
        //Channel channel = ctx.channel();
        //channel.writeAndFlush( Unpooled.copiedBuffer("小滴课堂 xdclass.net" ,CharsetUtil.UTF_8));

        //第二种
        //ChannelPipeline pipeline = ctx.pipeline();
        //pipeline.writeAndFlush( Unpooled.copiedBuffer("小滴课堂 xdclass.net" ,CharsetUtil.UTF_8));

        ByteBuf data = (ByteBuf) msg;

        System.out.println( "服务端收到数据: " + data.toString( CharsetUtil.UTF_8 ) );

        //在inboundhandler执行ChannelHandlerContext的fire方法,会调用下一个handler的相应方法
        //ctx.fireChannelRead(msg); //在当前handler中调用下一个handler中的channelRead(msg)方法,传递msg数据

        //第三种 (writeAndFlush(data) 将data写往下一个handler或者server)
        ctx.writeAndFlush( data );
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "EchoServerHandle channelReadComplete" );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelRegistered" );
        ctx.fireChannelRegistered();
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelUnregistered" );
        ctx.fireChannelUnregistered();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelActive" );
        ctx.fireChannelActive();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println( "channelInactive" );
        ctx.fireChannelInactive();
    }
}
