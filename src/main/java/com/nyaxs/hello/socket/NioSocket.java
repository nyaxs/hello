package com.nyaxs.hello.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-08 16:53
 */
public class NioSocket {
    int CAPACITY = 1024;
    private static final Logger log = LoggerFactory.getLogger(NioSocket.class);
    //保存客户端连接
    List<SocketChannel> channelList = new ArrayList<>();

    void connect() throws IOException {
        //创建NIO服务端Socket监听
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9002));
        //设为非阻塞
        serverSocketChannel.configureBlocking(false);
        while(!Thread.interrupted()){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                log.info("connected...");
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }

            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
                int read = sc.read(byteBuffer);
                if(read>0){
                    log.info("get data..."+new String(byteBuffer.array()));
                }else if(read ==-1){
                    iterator.remove();
                    log.info("lose connection...");
                }

            }

        }

    }



}
