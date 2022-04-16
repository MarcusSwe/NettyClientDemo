package me.code;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class EchoClient {

    private final String host; // host man vill ansluta sig till
    private final int port; // port man vill ansluta sig till

    public EchoClient(String host, int port) { // vart man vill ansluta sig till
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup(); // får hela ramverket att fungera - hanterar events
        try {
            Bootstrap bootstrap = new Bootstrap(); // startar igång klienten.. typ som att skapa en ny socket..
            Channel channel = bootstrap.group(group) // man vill använda eventloopen man skapa..Channel är själva uppkopplingen
                    .channel(NioSocketChannel.class) // hur vill hantera våra channels..
                    .handler(new EchoChannelInitializer()) // hanterar anslutningen..
                    .connect(this.host, this.port).sync().channel(); //ansluter till servern.. channel, hämta våran socket för att få tillgång till den senare som nedan.. sync gör den lättare att hantera..
                                                                     // väljer medvetet att blocka den för att få tillgång till min channel..alltså min socket.. skriva till sedan..
                                                                        // har man inte sync och channel..blir det async och den kommer bara fortsätta köra..
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            while (!line.equalsIgnoreCase("exit")) {
                ByteBuf buf = Unpooled.buffer(); // Nettys version av Byte array

                buf.writeBytes(line.getBytes());

                channel.writeAndFlush(buf); // skickar den med våran channel, våran socket, write and flush..skriver och skickar den samtidigt..skickas till servern..

                line = scanner.nextLine();
            }

        } catch (Exception ignored) {}
        finally {
            group.shutdownGracefully(); // stänger av våra eventloops.. GraceFully stänger av det på ett säkert sätt..har med trådar att göra..
        }
    }
}
