package com.coderdianxia.coderc.client;

import com.coderdianxia.coderc.initializer.HttpPipelineInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName: NioClient
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 14:43
 */
public class NioClient {

    private final String ip;
    private final int port;

    public NioClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new NioClient("localhost",9999).start();
    }

    public  void start() throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(ip, port)
                .handler(new HttpPipelineInitializer(true));
        ChannelFuture f = null;
        try {
            f = bootstrap.connect().sync();
        } catch (InterruptedException e) {
            f.channel().close();
        }finally{
            loopGroup.shutdownGracefully().sync();
        }
    }
}
