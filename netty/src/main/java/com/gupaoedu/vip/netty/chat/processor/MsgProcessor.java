package com.gupaoedu.vip.netty.chat.processor;

import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import io.netty.channel.Channel;

public class MsgProcessor {
    //TODO 改造两个sendMsg方法
    public void sendMsg(Channel channel, IMMessage msg) {
    }

    public void sendMsg(Channel channel, String msg) {
    }

    public String getAddress(Channel client) {
        return null;
    }
}
