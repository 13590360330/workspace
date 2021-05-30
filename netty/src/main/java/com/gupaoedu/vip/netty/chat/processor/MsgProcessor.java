package com.gupaoedu.vip.netty.chat.processor;

import com.gupaoedu.vip.netty.chat.protocol.IMDecoder;
import com.gupaoedu.vip.netty.chat.protocol.IMEncoder;
import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.JSONObject;

public class MsgProcessor {

    //记录在线用户
    private static ChannelGroup onlineUsers = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE );

    //定义一些扩展属性
    public static final AttributeKey<String> NICK_NAME = AttributeKey.valueOf( "nickName" );
    public static final AttributeKey<String> IP_ADDR = AttributeKey.valueOf( "ipAddr" );
    public static final AttributeKey<JSONObject> ATTRS = AttributeKey.valueOf( "attrs" );
    public static final AttributeKey<String> FROM = AttributeKey.valueOf( "from" );

    //自定义解码器
    private IMDecoder decoder = new IMDecoder();
    //自定义编码器
    private IMEncoder encoder = new IMEncoder();

    /**
     * 获取用户昵称
     *
     * @param client
     * @return
     */
    public String getNickName(Channel client) {
        return client.attr( NICK_NAME ).get();
    }

    /**
     * 获取用户远程IP地址
     *
     * @param client
     * @return
     */
    public String getAddress(Channel client) {
        return client.remoteAddress().toString().replaceFirst( "/", "" );
    }


    //TODO 改造两个sendMsg方法
    public void sendMsg(Channel channel, IMMessage msg) {
    }

    public void sendMsg(Channel channel, String msg) {
    }


}
