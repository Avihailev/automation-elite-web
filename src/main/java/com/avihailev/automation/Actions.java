package com.avihailev.automation;

import com.avihailev.automation.common.CommonActions;
import com.avihailev.automation.report.Reporter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions extends Reporter {

    private Element element;
    private TestStep step;
    private WebDriverWait wait;

    private static final Logger logger = LogManager.getLogger(Actions.class.getName());

    public Actions(Element element, TestStep step){
        logger.info("deciding what action to take for element " + step.getStepName());
        this.element = element;
        this.step = step;
    }

    public boolean action(){
        logger.info("taking action");
        this.wait = new WebDriverWait(element.getDriver().getWebDriver(), 30);
        try {
            switch (step.getKeywordAction()) {
                case Click: {
                    logger.info("clicking element");
                    wait.until(ExpectedConditions.elementToBeClickable(element.getElement()));
                    try {
                        element.getElement().click();
                    } catch (Exception exception){
                        System.out.println("exception: " + exception.getMessage());
                    }
                    return pass();
                }
                case Set: {
                    logger.info("setting text in field");
                    element.getElement().sendKeys(step.getActionValue());
                    return pass();
                }
                case CompareText: {
                    logger.info("comparing text between element and text provided");
                    if (element.getElement().getText().equals(step.getActionValue())){
                        logger.info("text is equal");
                        return pass();
                    } else {
                        logger.info("text is not equal");
                        return fail("element was found but element contains text: " +  element.getElement().getText() + " but text expected is: " + step.getActionValue());
                    }
                }
                default: {
                    String message = "action " + step.getKeywordAction().toString() + " is not valid";
                    logger.info(message);
                    return fail(message);
                }
            }
        } catch (Exception exception){
            String message = "failed to " + step.getKeywordAction().toString().toLowerCase() + " element";
            logger.info(message);
            return fail(message);
        } finally {
            printScreen(element.getDriver().getWebDriver());
        }
    }

    private boolean pass(){
        setPassed(true);
        setReportMessage("keyword found an action " + step.getKeywordAction().toString() + " preformed successfully");
        return true;
    }

    private boolean fail(String error){
        setPassed(false);
        setReportMessage("keyword found an action " + step.getKeywordAction().toString() + " failed");
        return false;

    }

    public TestStep getStep() {
        return step;
    }
}
