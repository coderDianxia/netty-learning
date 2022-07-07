package com.coderdianxia.coderc.initializer;

import com.coderdianxia.coderc.handler.in.HttpRequestHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @ClassName: HttpPipelineInitializer
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/6 14:50
 */
public class HttpPipelineInitializer extends ChannelInitializer<SocketChannel> {

    private boolean client;

    public HttpPipelineInitializer(boolean client){
        this.client = client;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        System.out.println("初始化"+ (client?"客户端":"服务端"));
        if(client)
        {
            pipeline.addLast("encoder",new HttpRequestEncoder()); //客户端发起请求时，对数据进行编码
            pipeline.addLast("decoder",new HttpResponseDecoder());//客户端接收服务端数据时，进行解码
        }
        else
        {
//            pipeline.addLast("coder",new HttpResponseEncoder()); //客户端发起请求时，对数据进行编码
//            pipeline.addLast("decoder",new HttpRequestDecoder());//客户端接收服务端数据时，进行解码
            pipeline.addLast("coder",new HttpServerCodec());//编解码
            pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));//消息聚合器
            pipeline.addLast("request",new HttpRequestHandler());//请求拦截
        }
    }
}
