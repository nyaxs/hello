package com.nyaxs.hello.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-08 15:29
 */
public class MainSocket {
    private static Logger log = LoggerFactory.getLogger(MainSocket.class);

    public static void main(String[] args) throws InterruptedException, IOException {
//        MultiThreadBlockSocket socketSupport = new MultiThreadBlockSocket(9000,2048);
//        new Thread(socketSupport).start();
//
//        ThreadPoolBlockSocket threadPoolBlockSocket = new ThreadPoolBlockSocket(9001);
//        threadPoolBlockSocket.createThreadPool();

        NioSocket nioSocket = new NioSocket();
//        nioSocket.connect();
        nioSocket.connectWithSelector();
        Thread.sleep(5000);
    }
}
