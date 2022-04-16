package me.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//hanterar alla meddelanden som kommer in..

public class EchoChannelHandler extends SimpleChannelInboundHandler<ByteBuf> { // vi säger vi vill ta in bytebuf..

    // event driven.. metod som automatiskt kallas på.. varje gång vi får in  ett meddelande.. från servern.. 

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffer = new byte[256];
        int count = byteBuf.readableBytes();
        byteBuf.readBytes(buffer, 0, count);
        System.out.println("From server: " + new String(buffer, 0, count));
    }
}
