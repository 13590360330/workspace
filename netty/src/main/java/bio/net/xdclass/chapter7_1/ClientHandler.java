package bio.net.xdclass.chapter7_1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf mes = null;
        byte [] req = ("xdclass.net"+System.getProperty("line.separator")).getBytes();
        //连续发送
        for(int i=0; i< 100; i++){
            mes = Unpooled.buffer(req.length);
            mes.writeBytes(req);
            ctx.writeAndFlush(mes);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
