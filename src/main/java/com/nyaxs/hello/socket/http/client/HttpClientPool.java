package com.nyaxs.hello.socket.http.client;

import com.nyaxs.hello.socket.netty.chat.client.MyClientHandler;
import com.nyaxs.hello.socket.netty.chat.client.NettyClientObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-14 16:37
 */
public class HttpClientPool {

    private static final Logger log  = LoggerFactory.getLogger(HttpClientPool.class);
    public static void clientPoolStart(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new MyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",9901).sync();
            Channel channel = channelFuture.channel();
            log.info("========"+channel.localAddress()+"=========");


            channel.closeFuture().sync();
        }catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
            group.shutdownGracefully();
        }


    }
}
