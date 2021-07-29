package com.avihailev.automation;

import com.avihailev.automation.common.CommonActions;
import com.avihailev.automation.types.ActionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;

public class Actions {

    private Element element;
    private TestStep step;
    private WebDriverWait wait;
    private String screenShot;

    private static final Logger logger = LogManager.getLogger(Actions.class.getName());

    public Actions(Element element, TestStep step){
        logger.info("deciding what action to take");
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
                    element.getElement().click();
                    return true;
                }
                case Set: {
                    logger.info("setting text in field");
                    element.getElement().sendKeys(step.getActionValue());
                    return true;
                }
                case CompareText: {
                    logger.info("comparing text between element and text provided");
                    if (element.getElement().getText().equals(step.getActionValue())){
                        logger.info("text is equal");
                        return true;
                    } else {
                        logger.info("text is not equal");
                        return false;
                    }
                }
                default: {
                    logger.info("action " + step.getKeywordAction().toString() + " is not valid");
                    return false;
                }
            }
        } catch (Exception exception){
            logger.info("failed to " + step.getKeywordAction().toString().toLowerCase() + " element");
            return false;
        } finally {
            screenShot = CommonActions.printScreen(element.getDriver().getWebDriver(), step);
        }
    }

    private void reportStep(){

    }

}
