package com.coderdianxia.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName: EchoServerHandler
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 10:03
 */
@ChannelHandler.Sharable  //表示该类的实例可以在被多个channelpipline添加，使用该注解要确保该实例是线程安全的
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("Server received: " + buf.toString(CharsetUtil.UTF_8));
        ctx.write(buf); //将数据写回给客户端，这时还没有冲刷数据，并不会真正返回给客户端

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);//数据全部读取完成，冲刷数据，返回给客户端
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();                //发生异常，进行捕获
        ctx.close();
    }
}
