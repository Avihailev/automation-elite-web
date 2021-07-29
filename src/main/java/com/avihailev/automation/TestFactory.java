package com.avihailev.automation;

import com.avihailev.automation.common.CommonActions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestFactory {

    private TestSuite testSuite;
    private final int THREAD_POOL_MAX_SIZE = 3;

    public TestFactory(TestSuite testSuite){
        this.testSuite = testSuite;
    }

    public void run(){
        CommonActions.killDrivers();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_MAX_SIZE);
        for (Test test : testSuite.getTests()){
            if (Validator.validateTest(test)) {
                TestThreadRunner testThreadRunner = new TestThreadRunner(test, testSuite.getSettings());
                executor.execute(testThreadRunner);
            }
        }
        executor.shutdown();
        CommonActions.killDrivers();
    }
}
