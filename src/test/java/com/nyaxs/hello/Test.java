package com.nyaxs.hello;

import com.nyaxs.hello.domain.SysUser;
import com.nyaxs.hello.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test {
    @Autowired
    private SysUserMapper mapper;

    @org.junit.jupiter.api.Test
    public void test(){
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("nya");
        sysUser.setNickName("hello");
        sysUser.setPassword("123456789");
        int insert = mapper.insert(sysUser);
        List<SysUser> sysUsers = mapper.selectList(null);
        sysUsers.stream().forEach(System.out::println);
    }
}
