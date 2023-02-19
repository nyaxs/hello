package com.nyaxs.hello.controller;

import com.nyaxs.hello.domain.BsMessage;
import com.nyaxs.hello.service.BsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

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

    public DeferredResult<BsMessage> longPollSend(BsMessage message){
        DeferredResult<BsMessage> deferredResult = new DeferredResult<>();

        deferredResult.setResult(message);

        return deferredResult;
    }

}
