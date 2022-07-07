package com.coderdianxia.coderc.encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName: StringToIntegerEncode
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 10:42
 */
public class StringToIntegerEncode extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {

        list.add(Integer.parseInt(s));
    }
}
