package com.gupaoedu.vip.netty.chat.client.handler;

import com.gupaoedu.vip.netty.chat.protocol.IMMessage;
import com.gupaoedu.vip.netty.chat.protocol.IMP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void channelActive(ChannelHandlerContext ctx) throws IOException {
        this.ctx = ctx;
        IMMessage message = new
                IMMessage( IMP.LOGIN.getName(), "Console", System.currentTimeMillis(), this.nickName );
        sendMsg( message );
        log.info( "成功连接服务器,已执行登录动作" );
        session();
    }

    /**
     * 发送消息
     *
     * @param msg
     * @return
     */
    private boolean sendMsg(IMMessage msg) {
        ctx.channel().writeAndFlush( msg );
        System.out.println( "继续输入开始对话..." );
        return msg.getCmd().equals( IMP.LOGOUT ) ? false : true;
    }

    /**
     * 收到消息后调用
     *
     * @param ctx
     * @param msg
     * @throws IOException
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, IMMessage msg) throws IOException {
        IMMessage m = (IMMessage) msg;
        System.out.println( (null == m.getSender() ? "" : (m.getSender() + ":")) +
                removeHtmlTag( m.getContent() ) );
    }

    public static String removeHtmlTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义Script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile( regEx_script, Pattern.CASE_INSENSITIVE );
        Matcher m_script = p_script.matcher( htmlStr );
        htmlStr = m_script.replaceAll( "" ); //过滤Script标签

        Pattern p_style = Pattern.compile( regEx_style, Pattern.CASE_INSENSITIVE );
        Matcher m_style = p_style.matcher( htmlStr );
        htmlStr = m_style.replaceAll( "" ); //过滤Style标签

        Pattern p_html = Pattern.compile( regEx_html, Pattern.CASE_INSENSITIVE );
        Matcher m_html = p_html.matcher( htmlStr );
        htmlStr = m_html.replaceAll( "" ); //过滤HTML标签

        return htmlStr.trim();
    }

    /**
     * 发生异常时调用
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info( "与服务器断开连接:" + cause.getMessage() );
        ctx.close();
    }
}
