package com.coderdianxia.coderc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @ClassName: ByteToIntegerCoder 编解码器
 * @Description: 降低了编解码器的可重用性
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:53
 */
public class ByteToIntegerCoder  extends ByteToMessageCodec<Integer> {

    /**
     * 将出站的消息编码为字节
     * @param channelHandlerContext
     * @param msg
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Integer msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg);
    }

    /**
     * 将入栈的消息解码，待全部解码完成，传输给下一个channelHandler实例
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.readInt());
    }
}
