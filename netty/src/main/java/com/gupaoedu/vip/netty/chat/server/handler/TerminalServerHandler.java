package com.gupaoedu.vip.netty.chat.server.handler;

import com.gupaoedu.vip.netty.chat.processor.MsgProcessor;
import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 对自定义协议的支持
 */
@Slf4j
public class TerminalServerHandler extends SimpleChannelInboundHandler<IMMessage> {

    private MsgProcessor processor = new MsgProcessor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws Exception {
        processor.sendMsg( ctx.channel(), msg );
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info( "Socket Client: 与客户端断开连接:" + cause.getMessage() );
        cause.printStackTrace();
        ctx.close();
    }

}
