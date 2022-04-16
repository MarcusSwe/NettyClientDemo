package me.code;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class EchoChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception { // kommer kallas på automatiskt eftersom Netty är eventdriven och inte block
        ChannelPipeline pipeline = socketChannel.pipeline(); // våran pipeline..

        pipeline.addLast(new EchoChannelHandler()); // handler som hanterar informationen..hanterar alla meddelanden som kommer in..
    }
}

//socketchannel är typ samma sak som en socket.. används för att skicka meddelanden.. ta emot meddelanden..
//initializer var ett objekt som hanterar anslutningar..
// när vi gjort en anslutning sätter vi upp våran pipeline..som ska hantera informationen.. med våran handler..