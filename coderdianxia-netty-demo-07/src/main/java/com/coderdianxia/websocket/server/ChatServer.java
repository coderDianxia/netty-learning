package com.coderdianxia.websocket.server;

import com.coderdianxia.websocket.init.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @ClassName: ChatServer
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/8 14:40
 */
public class ChatServer {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private static NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private static NioEventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    public ChannelFuture start(InetSocketAddress address) throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(address)
                .childHandler(new ChatServerInitializer(channelGroup));

        ChannelFuture channelFuture = bootstrap.bind();
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();

        return channelFuture;
    }

    public static void main(String[] args) throws InterruptedException {
        ChatServer chatServer = new ChatServer();
        ChannelFuture channelFuture  =null;

        channelFuture = chatServer.start(new InetSocketAddress(9999));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                chatServer.destory();
            }
        });

        channelFuture.channel().closeFuture().syncUninterruptibly();

        System.out.println("通道关闭");

    }


    public void destory(){
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
