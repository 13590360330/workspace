package demo;

import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.NettyRuntime;

/**
 * TODO 获取系统属性
 */
public class SystemDemo {
    public static void main(String[] args) {
        //获取netty总线程数,netty线程数是电脑最大线程数的2倍
        int max = Math.max( 1, SystemPropertyUtil.getInt( "nio.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2 ) );
        System.out.printf( "maxProcessors:{%s}\n", max );
    }
}
