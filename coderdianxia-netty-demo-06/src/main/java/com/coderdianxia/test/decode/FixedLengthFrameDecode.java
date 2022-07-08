package com.coderdianxia.test.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName: FixedLengthFrameDecode
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/7 17:43
 */
public class FixedLengthFrameDecode extends ByteToMessageDecoder {
    private int frameLength;

    public FixedLengthFrameDecode(int frameLength){
            if(frameLength <=0)
            {
                throw new IllegalArgumentException("长度需要大于0");
            }
            this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() >= frameLength)
        {
            list.add(byteBuf.readBytes(frameLength));
        }
    }
}
