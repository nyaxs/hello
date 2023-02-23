package com.nyaxs.hello;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-02-22 15:19
 */


public class Test1 {

    @Test
    public void test(){
        StringBuffer sb = new StringBuffer();
        sb.append("asdfasdfasd,").append("acxgvxc,").append("hujiohjnjk");
        String[] str = sb.toString().split(",");
        Arrays.stream(str).forEach(System.out::println);
        for(String s :str){
            if(s.length()<8){
                char[] c = new char[]{'0','0','0','0','0','0','0','0'};
                for(int i=0;i<s.length();i++){
                    c[i] = s.charAt(i);
                }
                System.out.println(String.valueOf(c));
                continue;
            }

            System.out.println(s);
        }
    }




    @Test
    void name() {

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int count =0;
        StringBuffer sb = new StringBuffer();
        String n = in.nextLine();
        for (int i = 0; i < n.length(); i++) {
            if((i+1)%8==0){
                sb.append(",");
            }
            sb.append(n.charAt(i));

        }
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case

            sb.append(in.nextLine());
            count++;

        }
        String[] str = sb.toString().split(",");
        for(String s :str){
            if(s.length()<8){
                char[] c = new char[]{'0','0','0','0','0','0','0','0'};
                for(int i=0;i<s.length();i++){
                    c[i] = s.charAt(i);
                }

                System.out.println(String.valueOf(c));
                continue;
            }
            System.out.println(s);

        }
    }

    @Test
    void t1() {
        long num = 1l;
        long k = (long)Math.sqrt(num);
//        Integer iiii = new Integer(22333);

         for (long i = 2; i <= k; i++) {
            // if(i>3 && i%2==0){
            //     continue;
            // }
            while (num % i == 0) {
                System.out.print(i + " ");
                num /= i;
            }
        }
        System.out.println(num ==1?"":num+"");
    }


    @Test
    void t2() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class<?> target = Class.forName("com.nyaxs.hello.domain.SysUser");

        Method[] declaredMethods = target.getDeclaredMethods();
        for (Method m :
                declaredMethods) {
            System.out.println("getMethodName--"+m.getName());
        }
        Field[] declaredFields = target.getDeclaredFields();
        for (Field f :
                declaredFields) {
            System.out.println("getFieldName--"+f.getName());
        }
        Field ff = target.getDeclaredField("userId");
        ff.setAccessible(true);
        ff.set(target,12121l);

        Method   getId = target.getDeclaredMethod("getUserId",String.class);
        System.out.println("getId--"+getId.invoke(target));
    }

    @Test
    void t3() {
        SmsService sms = (SmsService) DynamicProxyFactory.getProxy(new SmsServiceImpl());

        sms.send("helllo");
    }

    interface SmsService {
        public String send(String msg);
    }

    class SmsServiceImpl implements SmsService{

        @Override
        public String  send(String msg) {
            System.out.println("send message..."+msg);
            return msg;
        }
    }
    static class SmsInvocationHandler implements InvocationHandler{

        private    final Object target;
        public SmsInvocationHandler(Object target){
            this.target = target;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("start do some..."+method.getName());

            Object invoke = method.invoke(target, args);

            System.out.println("end do some..."+method.getName());

            return invoke;
        }



    }
    class DynamicProxyFactory{
        static Object getProxy(Object target){
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),new SmsInvocationHandler(target));
        }
    }



}
