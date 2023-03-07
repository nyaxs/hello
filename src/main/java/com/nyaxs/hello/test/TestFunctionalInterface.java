package com.nyaxs.hello.test;

import com.nyaxs.hello.support.PrintString;

import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-07 14:02
 */
public class TestFunctionalInterface {
    public static void main(String[] args) {
        String s = "hello my dear";
        printString(s,System.out::println);
        printString(s,TestFunctionalInterface::plus);
        Consumer consumer1 = System.out::println;
        Consumer<String> consumer2 = TestFunctionalInterface::plus;
    }

    static void printString(String s, PrintString printString){
        printString.print(s);
    }

    static void plus(String s){
        System.out.println("plus--"+s);
    }
}
