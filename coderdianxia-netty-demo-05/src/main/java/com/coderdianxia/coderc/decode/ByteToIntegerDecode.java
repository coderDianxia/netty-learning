package com.coderdianxia.coderc.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName: ByteToIntegerDecode
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 9:56
 */
@ChannelHandler.Sharable
public class ByteToIntegerDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("resolve byte to Integer decode");
        if(byteBuf.readableBytes() >=4)
        {
            list.add(byteBuf.readInt());
        }

    }
}
