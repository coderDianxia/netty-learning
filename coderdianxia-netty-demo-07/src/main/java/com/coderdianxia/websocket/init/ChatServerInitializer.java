package com.coderdianxia.websocket.init;

import com.coderdianxia.websocket.handler.HttpRequestHandler;
import com.coderdianxia.websocket.handler.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName: ChatServerInitializer
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/8 14:29
 */
public class ChatServerInitializer extends ChannelInitializer {

    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec(),
                new HttpObjectAggregator(8*1024),
                new ChunkedWriteHandler(),
                new HttpRequestHandler("/ws"),
                new WebSocketServerProtocolHandler("/ws"),
                new TextWebSocketFrameHandler(group));
    }
}
