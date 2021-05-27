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

    public static boolean isIMP(String content){
        return content.matches( "^\\[(SYSTEM|LOGIN|LOGIN|CHAT)\\]" );
    }

    IMP(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
