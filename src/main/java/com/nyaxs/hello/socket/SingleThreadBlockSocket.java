package com.nyaxs.hello.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-07 14:38
 */
public class SingleThreadBlockSocket {
    private static Logger log = LoggerFactory.getLogger(SingleThreadBlockSocket.class);
    public static void main(String[] args) throws IOException {

        //创建server socket 监听网路端口
        ServerSocket serverSocket = new ServerSocket(9000);
        log.info("start listen localhost:9000...");
        //循环等待网络输入
        while(!Thread.interrupted()){
            log.info("block wait accept data...");
            Socket acceptSocket = serverSocket.accept();
            //处理读取到的数据
            int i =0;
            while(i<5){
                handle(acceptSocket);
                i++;
                log.info(i+"");
            }
            log.info("current socket input handled...");
        }


        //关闭资源
    }

    private static void handle(Socket acceptSocket) throws IOException {
        byte[] bytes = new byte[1024];
        int read = acceptSocket.getInputStream().read(bytes);
        if(read!=-1){
            log.info("get data..."+new String(bytes,0,read));
        }
    }
}
