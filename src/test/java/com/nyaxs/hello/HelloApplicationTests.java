package com.nyaxs.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;

import static com.nyaxs.hello.controller.LongPollingController.watchRequests;

@SpringBootTest
class HelloApplicationTests {

    @Test
    void contextLoads() {
            if (watchRequests.containsKey("1")) {
                Collection<DeferredResult<String>> deferredResults = watchRequests.get("1");
                for (DeferredResult<String> deferredResult : deferredResults) {
                    deferredResult.setResult("helllllllo");
                }
            }
    }

}
