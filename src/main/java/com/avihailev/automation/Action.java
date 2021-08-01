package com.avihailev.automation;

import com.avihailev.automation.report.Reporter;
import com.avihailev.automation.types.ActionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
                        boolean success = false;
                        int i = 0;
                        while (!success && i < 9) {
                            try {
                                element.getElement().click();
                                success = true;
                            } catch (Exception e){
                                i++;
                                logger.error(i + " try to click button " + step.getStepName());
                                Thread.sleep(2000);
                            }
                        }
                        if (!success) {
                            element.getElement().click();
                        }
                    } catch (Exception exception){
                        logger.error("exception: " + exception.getMessage());
                        return fail("error in clicking " + step.getStepName());
                    }
                    return pass();
                }
                case Set: {
                    logger.info("setting text in field");
                    moveToElement();
                    for (int i = 0; i < step.getActionValue().length(); i++) {
                        element.getElement().sendKeys(String.valueOf(step.getActionValue().charAt(i)));
                        Thread.sleep(500);
                    }
                    return pass();
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
                    } catch (Exception exception) {
                        logger.error("exception: " + exception.getMessage());
                        return fail("error in context clicking " + step.getStepName());
                    }
                    return pass();
                }
                case Wait: {
                    try{
                        int waitTime = Integer.getInteger(step.getActionValue());
                        Thread.sleep((long) waitTime*1000);
                        pass();
                    } catch (Exception e){
                        String message = "failed to wait for element: " + step.getStepName() + " , please check if entered a number in action value";
                        logger.error(message);
                        fail(message);
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
}
