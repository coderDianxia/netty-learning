package com.coderdianxia.coderc.handler.in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @ClassName: HttpRequestHandler
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 16:17
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.uri();
        System.out.println("请求uri"+uri);
    }
}
