package com.avihailev.automation;

import com.avihailev.automation.report.Reporter;
import com.avihailev.automation.types.ActionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.ExecutionException;

public class Action extends Reporter {

    private Element element;
    private TestStep step;
    private WebDriverWait wait;

    private static final Logger logger = LogManager.getLogger(Action.class.getName());

    public Action(Element element, TestStep step){
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
                        moveToElement();
                        if (!click())
                            return fail("failed to click element: " + step.getStepName());
                        return pass();
                    } catch (Exception exception){
                        logger.error("exception: " + exception.getMessage());
                        return fail("error in clicking " + step.getStepName());
                    }
                }
                case Set: {
                    try {
                        logger.info("setting text in field");
                        moveToElement();
                        for (int i = 0; i < step.getActionValue().length(); i++) {
                            element.getElement().sendKeys(String.valueOf(step.getActionValue().charAt(i)));
                            Thread.sleep(500);
                        }
                        return pass();
                    } catch (Exception e){
                        logger.error("failed to set text to field " + step.getStepName());
                        return fail("failed to set text to field " + step.getStepName());
                    }
                }
                case CompareText: {
                    logger.info("comparing text between element and text provided");
                    moveToElement();
                    if (element.getElement().getText().equals(step.getActionValue())){
                        logger.info("text is equal");
                        return pass();
                    } else {
                        logger.info("text is not equal");
                        return fail("element was found but element contains text: " +  element.getElement().getText() + " but text expected is: " + step.getActionValue());
                    }
                }
                case DoubleClick: {
                    logger.info("double clicking the element");
                    wait.until(ExpectedConditions.elementToBeClickable(element.getElement()));
                    try {
                        moveToElement();
                        Actions a = new Actions(element.getDriver().getWebDriver());
                        a.doubleClick(element.getElement()).perform();
                    } catch (Exception exception) {
                        logger.error("exception: " + exception.getMessage());
                        return fail("error in double clicking " + step.getStepName());
                    }
                    return pass();
                }
                case ContextClick: {
                    logger.info("context clicking the element");
                    wait.until(ExpectedConditions.elementToBeClickable(element.getElement()));
                    try {
                        moveToElement();
                        Actions a = new Actions(element.getDriver().getWebDriver());
                        a.contextClick(element.getElement()).perform();
                        return pass();
                    } catch (Exception exception) {
                        logger.error("exception: " + exception.getMessage());
                        return fail("error in context clicking " + step.getStepName());
                    }
                }
                case Wait: {
                    try{
                        int waitTime = Integer.parseInt(step.getActionValue());
                        Thread.sleep((long) waitTime*1000);
                        return pass();
                    } catch (Exception e){
                        String message = "failed to wait for element: " + step.getStepName() + " , please check if entered a number in action value";
                        logger.error(message);
                        return fail(message);
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
        setReportMessage("keyword found an action " + step.getKeywordAction().toString() + " failed \n" + error);
        return false;

    }

    public TestStep getStep() {
        return step;
    }

    private void moveToElement(){
        if (step.getSettings().isMoveToElement() || step.getKeywordAction() == ActionType.MoveToElement) {
            logger.info("moving to element");
            try {
                Actions actions = new Actions(element.getDriver().getWebDriver());
                actions.moveToElement(element.getElement()).build().perform();
                //Thread.sleep(1000);
            } catch (Exception e) {
                logger.error("failed to move to element with this error: ");
                logger.error(e.getMessage());
            }
        }
    }

    private void moveToElementFromClick(){
        logger.info("moving to element");
        try {
            Actions actions = new Actions(element.getDriver().getWebDriver());
            actions.moveToElement(element.getElement()).build().perform();
            //Thread.sleep(1000);
        } catch (Exception e) {
            logger.error("failed to move to element with this error: ");
            logger.error(e.getMessage());
        }
    }

    private boolean click(){
        try {
            int i = 0;
            while (i < 5) {
                try {
                    element.getElement().click();
                    return true;
                } catch (Exception e) {
                    i++;
                    logger.error(i + " try to click button " + step.getStepName());
                    Thread.sleep(2000);
                }
            }
            setElementInTheMiddle();
            logger.info("trying to reclick element");
            element.getElement().click();
            return true;
        } catch (Exception e){
            logger.error("failed to click element with this error: ");
            logger.error(e.getMessage());
            return false;
        }
    }

    private void setElementInTheMiddle(){
        logger.info("setting element in the middle of the screen");
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) element.getDriver().getWebDriver()).executeScript(scrollElementIntoMiddle, element.getElement());
    }
}
