package com.coderdianxia.coderc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName: IntegerToStringCoder
 * @Description:消息转换的编解码器，参数化类
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:57
 */
public class IntegerToStringCoder extends MessageToMessageCodec<Integer,String> {

    /**
     * 处理出站消息类型outbound 到inbound
     * @param channelHandlerContext
     * @param s
     * @param list
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        list.add(Integer.parseInt(s));
    }

    /**
     * 处理入站消息类型inbound 到outbound
     * @param channelHandlerContext
     * @param integer
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer integer, List<Object> list) throws Exception {
        list.add(integer.toString());
    }
}
