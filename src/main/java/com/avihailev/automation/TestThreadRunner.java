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
        driver.enterUrl(test.getUrl());
        for (TestStep step : test.getSteps()){
            Element element = new Element(driver,step);
            element.find();
            //Actions actions = new Actions(element,);
        }
        //System.out.println("thread " + Thread.currentThread().getName());
        driver.kill();
    }
}
