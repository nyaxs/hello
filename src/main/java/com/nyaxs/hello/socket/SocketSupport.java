package com.nyaxs.hello.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-08 17:27
 */
public class SocketSupport {
    private static final Logger log = LoggerFactory.getLogger(SocketSupport.class);
    static int CAPACITY =1024;
    static void runSocketChannel(SocketChannel socketChannel){
        try{
            while (!Thread.interrupted()){
                System.out.println(Thread.currentThread().getName()+"---");

                ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
                int read = socketChannel.read(byteBuffer);
                if(read>0){
                    ByteBuffer processBuffer = socketChannelProcess(byteBuffer, read);
                    socketChannel.write(processBuffer);
                }else {
                    ByteBuffer out = ByteBuffer.wrap("failed".getBytes());
                    socketChannel.write(out);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    static ByteBuffer socketChannelProcess(ByteBuffer input,int read){
            System.out.println("input data..."+new String(input.array(),0,read));
            return ByteBuffer.wrap("success".getBytes());
    }
}
