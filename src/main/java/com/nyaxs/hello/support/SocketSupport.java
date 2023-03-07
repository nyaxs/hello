package com.nyaxs.hello.support;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-07 14:38
 */
public class SocketSupport {

    void connect(){

        try (ServerSocket socket = new ServerSocket(9999)){

            Socket accept = socket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
