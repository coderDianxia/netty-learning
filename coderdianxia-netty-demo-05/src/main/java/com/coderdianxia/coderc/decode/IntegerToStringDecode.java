package com.coderdianxia.coderc.decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @ClassName: IntegerToStringDecode
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:26
 */
public class IntegerToStringDecode extends MessageToMessageDecoder<Integer> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer msg, List<Object> list) throws Exception {
        System.out.println("将Integer转为String 解码器");
        list.add(String.valueOf(msg));
    }
}
