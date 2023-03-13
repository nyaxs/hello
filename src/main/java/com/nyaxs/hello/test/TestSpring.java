package com.nyaxs.hello.test;

import com.nyaxs.hello.domain.SysUser;
import com.nyaxs.hello.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-02-24 14:40
 */
//@RestController
public class TestSpring {

    @Autowired
    private SysUserMapper mapper;

    @Transactional(propagation= Propagation.REQUIRED)
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

