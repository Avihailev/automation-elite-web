package com.avihailev.automation;

import com.avihailev.automation.browsers.Driver;
import com.avihailev.automation.common.CommonActions;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Element {

    private By by;
    private WebDriverWait wait;
    private Driver driver;
    private TestStep step;
    private WebElement element = null;
    private String screenShot;
    private static final Logger logger = LogManager.getLogger(Element.class.getName());

    public Element(Driver driver, TestStep step){
        this.driver = driver;
        this.step = step;
        logger.info("configuring by what to locate element");
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
        logger.info("finding element");
        if (by != null){
            wait = new WebDriverWait(driver.getWebDriver(), 30);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            if (step.getSettings().isHighlightElement())
                ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", element);
            logger.info("element found");
            printScreen();
            return true;
        } else {
            logger.fatal("error in deploying by what to locate element, cant find element");
            printScreen();
            //
        }
        return false;
    }

    private void printScreen(){
        screenShot = CommonActions.printScreen(driver.getWebDriver(),step);
    }

    public WebElement getElement() {
        return element;
    }

    public Driver getDriver() {
        return driver;
    }
}
