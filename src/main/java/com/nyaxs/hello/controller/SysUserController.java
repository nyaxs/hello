package com.nyaxs.hello.controller;

import com.nyaxs.hello.domain.SysUser;
import com.nyaxs.hello.mapper.SysUserMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-02-24 15:12
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserMapper mapper;
    @GetMapping("all")
    public String getAll(){
        List<SysUser> sysUsers = mapper.selectList(null);
        return sysUsers.toString();
    }
    @Transactional(propagation= Propagation.REQUIRED)
    @GetMapping("tx")
    public  void test() {

        System.out.println("start tx...");
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("ddd");
        sysUser.setNickName("ddd");
        sysUser.setPassword("123456789");
        int insert = mapper.insert(sysUser);
        List<SysUser> sysUsers = mapper.selectList(null);
        System.out.println(sysUsers.toString());
        System.out.println("end tx...");

    }
}
