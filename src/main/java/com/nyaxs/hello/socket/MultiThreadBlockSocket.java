package com.nyaxs.hello.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-08 14:47
 */
public  class MultiThreadBlockSocket implements Runnable{

    private  final int PORT;
    protected static int MAX_SIZE = 1024;
    public MultiThreadBlockSocket(int port){
        this.PORT = port;
    }
    public MultiThreadBlockSocket(int port, int buffSize){
        this.PORT = port;
        MAX_SIZE = buffSize;
    }
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (!Thread.interrupted()){
                new Thread(new Handler(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Handler implements Runnable{
        final Socket socket;

        Handler(Socket s){
            this.socket = s;
        }
        @Override
        public void run() {
            try{
                System.out.println(Thread.currentThread().getName()+"---");
                while (!Thread.interrupted()){

                    byte[] bytes = new byte[MAX_SIZE];
                    int read = socket.getInputStream().read(bytes);
                    if(read!=-1){
                        byte[] outBytes = process(bytes, read);
                        socket.getOutputStream().write(outBytes);
                    }
                    socket.getOutputStream().write("failed".getBytes());
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            }

        private byte[] process(byte[] input,int read){
            System.out.println("input data..."+new String(input,0,read));
            byte[] outBytes = new byte[MAX_SIZE];
            outBytes = "success".getBytes();
            return outBytes;
        }
    }
}
