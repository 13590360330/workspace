package com.gupaoedu.vip.netty.chat.client.handler;

import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import com.gupaoedu.vip.netty.chat.protocol.IMP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

/**
 * 控制台消息处理
 * 聊天客户端逻辑实现
 *
 * @author 刘成
 */
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<IMMessage> {

    private ChannelHandlerContext ctx;
    private String nickName;

    public ChatClientHandler(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 启动客户端控制台
     */
    private void session() throws IOException {
        new Thread() {
            public void run() {
                System.out.println( nickName + ",你好,请在控制台输入对话内容" );
                IMMessage message = null;
                Scanner scanner = new Scanner( System.in );
                do {
                    if (scanner.hasNext()) {
                        String input = scanner.nextLine();
                        if ("exit".equals( input )) {
                            message = new IMMessage( IMP.LOGOUT.getName(), "Console",
                                    System.currentTimeMillis(), nickName );
                        } else {
                            message = new IMMessage( IMP.CHAT.getName(),
                                    System.currentTimeMillis(), nickName, input );
                        }
                    }
                }
                while (sendMsg( message ));
                scanner.close();
            }
        }.start();
    }

    /**
     * TCP 链路建立成功后调用
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        IMMessage message = new
                IMMessage( IMP.LOGIN.getName(), "Console", System.currentTimeMillis(), this.nickName );
    }

    private boolean sendMsg(IMMessage message) {
        return false;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws Exception {

    }
}
