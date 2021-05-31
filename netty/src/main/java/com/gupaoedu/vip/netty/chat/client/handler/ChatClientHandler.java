package com.gupaoedu.vip.netty.chat.client.handler;

import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<IMMessage> {
    public ChatClientHandler(String nickName) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws Exception {

    }
}
