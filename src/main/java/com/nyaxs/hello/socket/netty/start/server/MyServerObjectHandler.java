package com.nyaxs.hello.socket.netty.start.server;

import com.nyaxs.hello.domain.SysUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.nyaxs.hello.socket.netty.start.server.MyServerHandler.channelGroup;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-10 14:29
 */
public class MyServerObjectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SysUser u = (SysUser) msg;
        System.out.println("get object..."+u.toString());
        channelGroup.writeAndFlush(u);
    }
}
