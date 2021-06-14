package bio.net.xdclass.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import java.nio.channels.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class EchoClient {

    private String host;

    private int port;

    public EchoClient(String host, int port){
        this.host = host;
        this.port = port;
    }



    public void start() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)

                    .channel(NioSocketChannel.class)

                    .remoteAddress(new InetSocketAddress(host, port))

                    .handler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            //连接到服务端，connect是异步连接，在调用同步等待sync，等待连接成功
            ChannelFuture channelFuture = bootstrap.connect().sync();

            //阻塞直到客户端通道关闭
            channelFuture.channel().closeFuture().sync();

        }finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }

    }

    public static void main(String []args) throws InterruptedException, IOException {
        new EchoClient("127.0.0.1",8080).start();
    }
}
