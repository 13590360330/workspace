package com.gupaoedu.vip.netty.chat.protocol;

public enum IMP {
    /**
     * 系统消息
     */
    SYSTEM( "SYSTEM" ),
    /**
     * 系统消息
     */
    LOGIN( "LOGIN" ),

    LOGOUT( "LOGOUT" ),

    CHAT( "CHAT" ),

    FLOWER( "FLOWER" );

    private String name;

    IMP(String name) {
        this.name = name;
    }
}
