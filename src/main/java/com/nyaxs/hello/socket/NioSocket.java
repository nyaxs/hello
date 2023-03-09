package com.nyaxs.hello.socket;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    //NIO 使用 Selector，响应式IO
    void connectWithSelector() throws IOException {
        //创建服务监听
        ServerSocketChannel serverSocketChannel = createSocket(9003);
        //创建Selector处理channel，创建epoll
        Selector selector = Selector.open();
        //将ServerSocketChannel注册到Selector，并监听Accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("service 9003 started...");
        while(!Thread.interrupted()){
            //阻塞等待事件发生
            selector.select();
            //获取selector注册的所有事件key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //遍历事件key并处理
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //服务channel连接事件
                if(key.isAcceptable()){
                    ServerSocketChannel selectServerSocket = (ServerSocketChannel) key.channel();
                    SocketChannel clientSocket = selectServerSocket.accept();
                    clientSocket.configureBlocking(false);
                    clientSocket.register(selector, SelectionKey.OP_READ);
                    log.info("client socket connected...");
                }
                //客户端连接可读
                else if(key.isReadable()){
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = clientChannel.read(byteBuffer);
                    if (read>0){
                        log.info(new String(byteBuffer.array(),0,read));
                    }else if (read==-1){
                        log.info("lose client connect...");
                        clientChannel.close();
                    }
                }
                //本次事件处理完，移除，防止重复消费
                iterator.remove();
            }

        }


    }

    void connect() throws IOException {
        ServerSocketChannel serverSocketChannel = createSocket(9002);
        listenAcceptProcessSocket(serverSocketChannel);
    }

    private void iteratorSocketRead(List<SocketChannel> channels) throws IOException {
        Iterator<SocketChannel> iterator = channels.iterator();
        while (iterator.hasNext()){
            SocketChannel sc = iterator.next();
            ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
            int read = sc.read(byteBuffer);
            if(read>0){
                log.info("get data..."+new String(byteBuffer.array(),0, byteBuffer.position()));
            }else if(read ==-1){
                iterator.remove();
                log.info("lose connection...");
            }
        }
    }

    private ServerSocketChannel createSocket(int port) throws IOException {
        //创建NIO服务端Socket监听
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //设为非阻塞
        serverSocketChannel.configureBlocking(false);
        return serverSocketChannel;
        }

    private void listenAcceptProcessSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        while(!Thread.interrupted()){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                log.info("connected...");
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }
            iteratorSocketRead( channelList);

        }
    }

}




