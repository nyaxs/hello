package com.nyaxs.hello;

import com.nyaxs.hello.domain.SysUser;
import com.nyaxs.hello.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class Test {
    @Autowired
    private SysUserMapper mapper;

    @org.junit.jupiter.api.Test
    public void test(){
//        SysUser sysUser = new SysUser();
//        sysUser.setLoginName("nya");
//        sysUser.setNickName("hello");
//        sysUser.setPassword("123456789");
//        int insert = mapper.insert(sysUser);
//        List<SysUser> sysUsers = mapper.selectList(null);
//        sysUsers.stream().forEach(System.out::println);
        List<Integer> a = new ArrayList<>();
        a.add(19);
        a.add(8);
        a.add(60);
        a.add(10);
        a.add(1);
        a.add(8);
        a.add(1);
        a.stream().distinct().sorted().forEach(System.out::println);
String s = "adfasdf";
        char c = s.charAt(2);
        char[] cc = new char[]{'0','0','0','0','0','0','0','0'};

//        int[] aa = new int[2];
//        aa[0] = 11;
//        aa[1] = 8;
//        Arrays.stream(aa).forEach(System.out::println);
//
//        Arrays.sort(aa);
//        Arrays.stream(aa).forEach(System.out::println);
    }
}
