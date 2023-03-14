package com.nyaxs.hello.socket.http.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-13 13:49
 */
public class GlobalFilterHandler extends ChannelInboundHandlerAdapter {

    void sendBack(ChannelHandlerContext ctx, String res, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,status,Unpooled.copiedBuffer(res,CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    void uriParse(String uri){

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        System.out.println(request.headers());
        String res = "";
        try{
            String uri = request.uri();
            String content = request.content().toString(CharsetUtil.UTF_8);
            HttpMethod method = request.method();
            if(!"/api".equals(uri)){
                sendBack(ctx, "非法请求"+uri,HttpResponseStatus.BAD_REQUEST);
                return;
            }
            if (HttpMethod.GET.equals(method)) {
                res = "get response--content--"+content;
            } else if (HttpMethod.POST.equals(method)){
                res = "post response--content--"+content;
            }else {
                res = "unsupport response--content--"+content;
            }
            sendBack(ctx, res,HttpResponseStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            request.release();
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"connected...");
    }
}
