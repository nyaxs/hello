package com.nyaxs.hello.socket.netty.start.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-09 15:34
 */
public class NettyServer {
    public static void main(String[] args) {
        //创建线程模型
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        //装配启动服务监听
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder())
//                                    .addLast(new StringDecoder())
                                    .addLast(new ObjectDecoder(10240, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
//                                    .addLast(new MyServerHandler())
                                    .addLast(new MyServerObjectHandler())
                            ;
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(9900).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException interruptedException){
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
