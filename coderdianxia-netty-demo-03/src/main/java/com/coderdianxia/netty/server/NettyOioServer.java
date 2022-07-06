package com.coderdianxia.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @ClassName: NettyNioServer
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 17:24
 */
public class NettyOioServer {

    private  final int port;
    public NettyOioServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyOioServer(9993).start();
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .channel(OioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buf = Unpooled.unreleasableBuffer(
                                        Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
                                ctx.writeAndFlush(buf)
                                        .addListener(ChannelFutureListener.CLOSE);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind().sync();

        channelFuture.channel().close();

        eventLoopGroup.shutdownGracefully().sync();
    }
}
