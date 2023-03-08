package com.nyaxs.hello.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-08 16:02
 */
public class ThreadPoolBlockSocket {
    int coreSize = 8;
    int maxSize = 32;
    int queueCapacity = 100;
    long keepAlive = 10l;

    int port = 9001;
    public ThreadPoolBlockSocket(int port){
        this.port = port;
    }

    void createThreadPool() throws IOException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxSize, keepAlive, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(queueCapacity), new ThreadPoolExecutor.CallerRunsPolicy());
        ServerSocket serverSocket = new ServerSocket(port);
        while (!Thread.interrupted()){
            executor.execute(new HandlerSocket(serverSocket.accept()));
        }

    }
}
