package com.avihailev.automation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @PostMapping("/test")
    public String startTest(@RequestBody TestSuite newTestSuite){
        Thread newThread = new Thread(() -> {
            Application application = new Application(newTestSuite);
        });
        newThread.start();
        return "test ended";
    }

}
