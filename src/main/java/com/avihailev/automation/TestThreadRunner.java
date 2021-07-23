package com.avihailev.automation;

import com.avihailev.automation.browsers.Driver;

public class TestThreadRunner implements Runnable {

    private Test test;

    public TestThreadRunner(Test test){
        this.test = test;
    }

    @Override
    public void run() {
        Driver driver = new Driver(test.getTestBrowser());
        //System.out.println("thread " + Thread.currentThread().getName());
        driver.kill();
    }
}
