package com.avihailev.automation;

import com.avihailev.automation.browsers.Driver;
import com.avihailev.automation.report.Reporter;
import com.avihailev.automation.types.ActionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Element extends Reporter {

    private final By by;
    private WebDriverWait wait;
    private final Driver driver;
    private final TestStep step;
    private WebElement element = null;
    private static final Logger logger = LogManager.getLogger(Element.class.getName());

    public Element(Driver driver, TestStep step){
        this.driver = driver;
        this.step = step;
        logger.info("configuring by what to locate element: " + step.getStepName());
        switch (step.getFindKeywordBy().toLowerCase()){
            case "id":{
                logger.info("selected by id");
                this.by = By.id(step.getKeywordValue());
                break;
            }
            case "xpath":{
                logger.info("selected by xpath");
                this.by = By.xpath(step.getKeywordValue());
                break;
            }
            case "name":{
                logger.info("selected by name");
                this.by = By.name(step.getKeywordValue());
                break;
            }
            default:{
                logger.info("selected by css selector");
                this.by = By.cssSelector(step.getKeywordValue());
            }
        }
    }

    public boolean find(){
        try {
            if (step.getKeywordAction() == ActionType.Wait){
                return true;
            }
            if (step.getSettings().getWaitBetween() > 0){
                try {
                    Thread.sleep((long) step.getSettings().getWaitBetween() * 1000);
                } catch (InterruptedException interruptedException){
                    logger.error("failed to wait between steps");
                }
            }
            logger.info("finding element: " + step.getStepName());
            if (by != null) {
                wait = new WebDriverWait(driver.getWebDriver(), 30);
                try {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                } catch (TimeoutException toEx) {
                    logger.error("element: " + step.getKeywordValue() + " for step: " + step.getStepName() + " wasn't found");
                }
                if (step.getSettings().isHighlightElement())
                    ((JavascriptExecutor)driver.getWebDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
                logger.info("element " + step.getStepName() + " found");
                printScreen(driver.getWebDriver());
                setPassed(true);
                return true;
            } else {
                logger.fatal("error in deploying by what to locate element, cant find element");
                setPassed(false);
                setReportMessage("element wasn't found");
                printScreen(driver.getWebDriver());
            }
            return false;
        } catch (Exception exception){
            logger.error("error in locating element");
            printScreen(driver.getWebDriver());
            setPassed(false);
            return false;
        }
    }

    public WebElement getElement() {
        return element;
    }

    public Driver getDriver() {
        return driver;
    }

    public TestStep getStep() {
        return step;
    }
}
