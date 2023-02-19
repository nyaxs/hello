package com.nyaxs.hello.controller;

import com.nyaxs.hello.domain.BsMessage;
import com.nyaxs.hello.service.BsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class MessageController {

    @Autowired
    private BsMessageService msgService;
    @PostMapping("send")
    public String send(@RequestBody BsMessage message){

        msgService.insert(message);


        return "success";
    }
}
