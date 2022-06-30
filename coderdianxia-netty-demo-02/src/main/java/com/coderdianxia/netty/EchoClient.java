package com.coderdianxia.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName: EchoClient
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 10:36
 */
public class EchoClient {
    private final String host;
    private final int port;
    public EchoClient(String host,int port) {
        this.port = port;
        this.host = host;
    }

    public static void main(String[] args) {
        try {
            new EchoClient("localhost",9999).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(); //eventLoop 线程池组
        try{
            Bootstrap bootstrap = new Bootstrap(); //客户端配置文件
            bootstrap.group(eventLoopGroup)  //配置线程
                    .channel(NioSocketChannel.class) //使用NIO传输类型的channel
                    .remoteAddress(host,port) //服务器远程ip:端口
                    .handler(new ChannelInitializer<SocketChannel>(){  //为每一个新建立的channel添加该拦截器

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new EchoClientHandler());
                            socketChannel.pipeline().addLast(new EchoClientHandler2());//添加多个，事件仅有一个触发channelRead0
                        }
                    });
            ChannelFuture f = bootstrap.connect().sync(); //进行连接

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully().sync(); //释放资源
        }
    }

}
