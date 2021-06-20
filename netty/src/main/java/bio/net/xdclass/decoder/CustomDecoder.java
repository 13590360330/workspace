package bio.net.xdclass.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class CustomDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // int 是4个字节
        if(in.readableBytes()>=4){
            //添加到解码信息里面去
            out.add(in.readInt());
        }


    }
}
