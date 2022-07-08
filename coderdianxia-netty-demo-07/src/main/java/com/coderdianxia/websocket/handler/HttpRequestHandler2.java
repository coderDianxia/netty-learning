package com.coderdianxia.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * @ClassName: HttpRequestHandler
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/8 10:49
 */
public class HttpRequestHandler2 extends MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, List<Object> list) throws Exception {

    }
}
