package com.avihailev.automation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;

public class Driver {

    private URL driverLocation;
    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger(Driver.class.getName());

    public Driver(){
        logger.info("creating driver");
        try {
            System.setProperty("webdriver.chrome.driver", getDriverLocation());
            driver = new ChromeDriver();
        } catch (Exception exception){
            logger.fatal("creating driver failed");
        }

    }

    private String getDriverLocation(){
        logger.info("getting chrome driver path");
        driverLocation = Driver.class.getClassLoader().getResource("chromedriver.exe");
        return driverLocation.getPath();
    }

    public WebDriver getWebDriver() {
        return driver;
    }
}
