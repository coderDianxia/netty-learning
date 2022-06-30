package com.coderdianxia.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;

/**
 * @ClassName: EchoServer
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 9:33
 */
public class EchoServer {

    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        try {
            new EchoServer(9999).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(); //配置EventLoop线程池，用于处理新的连接和读写数据
        try {
            ServerBootstrap bootstrap = new ServerBootstrap(); //服务器的引导配置，配置线程池,选择通信类型，绑定端口，初始化拦截器等
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)  //使用nio传输作为channel
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() { //为每一个新建的channel 添加此拦截器

                        @Override
                        protected void initChannel(SocketChannel  serverSocketChannel) throws Exception {
                            serverSocketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());

            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully().sync(); //关闭线程池和释放所有资源
        }
    }
}
