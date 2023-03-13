package com.nyaxs.hello.socket.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-13 13:35
 */
public class ChannelHandlerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encoder", new HttpResponseEncoder())
                .addLast("decoder", new HttpRequestDecoder())
                .addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024))
                .addLast("compressor", new HttpContentCompressor())
                .addLast("busi", new BusiHandler());
    }
}
