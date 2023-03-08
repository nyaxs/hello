package com.nyaxs.hello.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class HandlerSocketChannel implements Runnable{
        int MAX_SIZE = 1024;
        final SocketChannel socketChannel;

    HandlerSocketChannel(SocketChannel s){
            this.socketChannel = s;
        }
        @Override
        public void run() {
            try{
                while (!Thread.interrupted()){
                    System.out.println(Thread.currentThread().getName()+"---");

                    byte[] bytes = new byte[MAX_SIZE];
                    ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_SIZE);
                    int read = socketChannel.read(byteBuffer);
                    if(read>0){
                        ByteBuffer processBuffer = process(byteBuffer, read);
                        socketChannel.write(processBuffer);
                    }
                    ByteBuffer out = ByteBuffer.wrap("failed".getBytes());
                    socketChannel.write(out);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            }

        private ByteBuffer process(ByteBuffer input,int read){
            System.out.println("input data..."+new String(input.array(),0,read));
            byte[] outBytes = new byte[MAX_SIZE];
            outBytes = "success".getBytes();
            return ByteBuffer.wrap(outBytes);
        }
    }