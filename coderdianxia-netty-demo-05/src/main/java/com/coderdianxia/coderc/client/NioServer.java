package com.coderdianxia.coderc.client;

import com.coderdianxia.coderc.initializer.HttpPipelineInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName: NioClient
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 14:43
 */
public class NioServer {

    private final int port;

    public NioServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new NioServer(9999).start();
    }

    public  void start() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new HttpPipelineInitializer(false));
        ChannelFuture f = bootstrap.bind().sync();
        f.channel().close().sync();

//        bossGroup.shutdownGracefully().sync();
        workGroup.shutdownGracefully().sync();

    }
}
