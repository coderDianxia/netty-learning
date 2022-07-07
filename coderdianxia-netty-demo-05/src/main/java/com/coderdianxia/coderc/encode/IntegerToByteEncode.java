package com.coderdianxia.coderc.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName: IntegerToByteEncode
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:40
 */
public class IntegerToByteEncode extends MessageToByteEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Integer msg, ByteBuf byteBuf) throws Exception {
        System.out.println("编码器:将Integer转Byte");
        byteBuf.writeInt(msg);
    }
}
