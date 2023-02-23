package com.nyaxs.hello;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NormalTest {

    @Test
    void name() {
        double a = 5.5;
        String aa = String.valueOf(a);
        String[] s = aa.split(".");
        String x = s[0];
        Integer y = Integer.parseInt(x);
        String t = s[1];
        char c = t.charAt(0);
        if(Integer.parseInt(String.valueOf(c))>=5){
            System.out.println(y+1);
        }else{
            System.out.println(y);
        }
    }

    @Test
    void t1() {
        String a = "5 5";
        int i = a.indexOf(".");
        System.out.println(i);
        String[] split = a.split(" ");
        char[] aaa =new char[]{'0','1'};
        String.valueOf(aaa);

        StringBuilder sb =new StringBuilder(a);
        sb.reverse();
        Object[] objects = Arrays.stream(split).distinct().collect(Collectors.toList()).toArray();

        int  x = 9;
            char xx = '1';
        char yy = '1';

        if (xx==yy) {
            System.out.println("sdaf");
        }
        //        int length = split.length;
//        Arrays.stream(split).forEach(System.out::println);

        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(9,1);
        map.put(2,2);
        map.put(6,33);
        map.put(98,14);
        Set<Integer> integers = map.keySet();
        Comparator.reverseOrder();
        integers.stream().sorted().forEach(System.out::println);
        integers.forEach(System.out::println);



        String s = "asdfasdf";
        String[] arr = s.split("");
        long count = Arrays.stream(arr).distinct().count();

    }
}
