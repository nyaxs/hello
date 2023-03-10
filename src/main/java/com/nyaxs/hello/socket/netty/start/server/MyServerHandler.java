package com.nyaxs.hello.socket.netty.start.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-09 17:15
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log  = LoggerFactory.getLogger(MyServerHandler.class);
    //GlobalEventExecutor.INSTANCE 全局事件执行器，单例
    static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //表示channel处于就绪状态，提示上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String onlineMsg = "[ client ]" + channel.remoteAddress() + " online " + sdf.format(new Date()) + "\n";
        //traverse channel in channelGroup,send onlineMsg
        log.info("channelGroup size..."+channelGroup.size());
        channelGroup.writeAndFlush(onlineMsg);
        channelGroup.add(channel);
        log.info("channelGroup size..."+channelGroup.size());
        log.info(channel.remoteAddress()+"online");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        String toPublicMsg = " client " + channel.remoteAddress() + " send message: " + s + "\n";
        String toSelfMsg = " self " + channel.remoteAddress() + " send message: " + s + "\n";
        log.info(toPublicMsg);
        channelGroup.forEach(c ->{
            if(c == channel){
                c.writeAndFlush(toSelfMsg);
            }else {
                c.writeAndFlush(toPublicMsg);
            }
        });
    }

    //channel disconnect
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String offLine = "[ client ]"+channel.remoteAddress()+" offline \n";
        channelGroup.writeAndFlush(offLine);
        log.info("channelGroup size..."+channelGroup.size());
//        channelGroup.remove(channel);
        log.info(channel.remoteAddress()+"offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
