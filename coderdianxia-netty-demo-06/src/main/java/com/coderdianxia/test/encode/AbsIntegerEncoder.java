package com.coderdianxia.test.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName: AbsIntegerEncoder
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/8 10:08
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        while(buf.readableBytes() >=4)
        {
            int abs = Math.abs(buf.readInt());
            list.add(abs);
        }
    }
}
