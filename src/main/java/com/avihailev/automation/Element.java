package com.avihailev.automation;

import com.avihailev.automation.browsers.Driver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Element {

    private By by;
    private WebDriverWait wait;
    private Driver driver;
    private WebElement element = null;
    private static final Logger logger = LogManager.getLogger(Element.class.getName());

    public Element(Driver driver, TestStep step){
        this.driver = driver;
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

    public WebElement find(){
        logger.info("finding element");
        if (by != null){
            wait = new WebDriverWait(driver.getWebDriver(), 30);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            logger.info("element found");
        } else {
            logger.fatal("error in deploying by what to locate element, cant find element");
        }
        return element;
    }

}
