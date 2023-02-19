package com.nyaxs.hello.controller;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;

@RestController
@RequestMapping("hello")
public class LongPollingController {
    private static Logger log = LoggerFactory.getLogger(HelloController.class);
    private static Long TIME_OUT = 30000L;
    public static Multimap<String, DeferredResult<String>> watchRequests = Multimaps.synchronizedMultimap(HashMultimap.create());

    @GetMapping("watch/{id}")
    public DeferredResult<String> watch(@PathVariable("id") String id) {
        log.info("监听--{}",id);
        DeferredResult<String> deferredResult = new DeferredResult<>(TIME_OUT);
//        deferredResult.onCompletion(
//                () -> watchRequests.remove(id, deferredResult)
//        );
        log.info("放入监听列表");
        watchRequests.put(id, deferredResult);
        log.info(watchRequests.toString());
        log.info(deferredResult.toString());
        return deferredResult;
    }

    @GetMapping("publish/{id}/{msg}")
    public String publish(@PathVariable String id, @PathVariable String msg) {
        log.info("发布消息{}----{}", id, msg);
        if (watchRequests.containsKey(id)) {
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id);
            for (DeferredResult<String> deferredResult : deferredResults) {
                log.info("遍历监听id--{}的对象deferredResult--{}发送消息{}",id,deferredResult,msg);
                deferredResult.setResult(msg);
            }
        }
        log.info("返回成功");
        return "Success";
    }

    @GetMapping("say")
    public String say() {
        return "hello";
    }


}
