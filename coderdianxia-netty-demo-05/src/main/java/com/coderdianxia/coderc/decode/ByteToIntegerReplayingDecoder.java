package com.coderdianxia.coderc.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ClassName: ReplayingDecoderImp
 * @Description: 不需要判断缓冲区是否有可读字节，有足够的字节进行读取，反之不读
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:19
 */
public class ByteToIntegerReplayingDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.readInt());
    }
}
