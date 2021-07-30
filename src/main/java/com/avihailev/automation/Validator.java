package com.avihailev.automation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Validator {

    private static final Logger logger = LogManager.getLogger(Validator.class.getName());


    public static boolean validateTestStep(TestStep step){
        if (step.getStepName() == null){
            logger.error("step don't have a name therefore step didn't run");
            return false;
        }
        if (step.getStepId() == 0){
            logger.error("step " + step.getStepName() + " has no id, therefore the step didn't run");
            return false;
        }
        if (step.getFindKeywordBy() == null || step.getKeywordValue() == null){
            logger.error("step " + step.getStepName() + " doesn't have a keyword or keyword value, therefore the step didn't run");
            return false;
        }
        if (step.getKeywordAction() == null){
            logger.error("step " + step.getStepName() + " doesn't have a action, therefore the step didn't run");
            return false;
        }
        return true;
    }

    public static boolean validateTest(Test test){
        if (test.getTestName() == null){
            logger.error("test doesn't have a test name, therefore the test didn't run");
            return false;
        }
        if (test.getTestId() == 0){
            logger.error("test " + test.getTestName() + " doesn't have a test id, therefore test didn't run");
            return false;
        }
        if (test.getTestBrowser() == null){
            logger.error("test " + test.getTestName() + " doesn't have a test browser or browser supplied is not correct, therefore test didn't run");
            return false;
        }
        if (test.getSteps() == null || test.getSteps().isEmpty()){
            logger.error("test " + test.getTestName() + " doesn't have steps therefore test didn't run");
            return false;
        }
        return true;
    }

    public static boolean validateSuite(TestSuite suite){
        if (suite.getSuiteName() == null){
            logger.error("suite doesn't have a suite name, therefore suite didn't run");
            return false;
        }
        if (suite.getSuiteId() == 0){
            logger.error("suite " + suite.getSuiteName() + " doesn't have suite id, therefore suite didn't run");
            return false;
        }
        if (suite.getSuitePlatform() == null){
            logger.error("suite " + suite.getSuiteName() + " doesn't have a suite platform or platform supplied is not correct, therefore suite didn't run");
            return false;
        }
        if (suite.getTests() == null || suite.getTests().isEmpty()){
            logger.error("suite " + suite.getSuiteName() + " doesn't have tests, therefore suite didn't run");
            return false;
        }
        return true;
    }
}
