package com.nyaxs.hello.socket;

import java.io.IOException;
import java.net.Socket;

class HandlerSocket implements Runnable{
        int MAX_SIZE = 1024;
        final Socket socket;

    HandlerSocket(Socket s){
            this.socket = s;
        }
        @Override
        public void run() {
            try{
                while (!Thread.interrupted()){
                    System.out.println(Thread.currentThread().getName()+"---");

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