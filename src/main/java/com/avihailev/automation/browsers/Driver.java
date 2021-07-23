package com.avihailev.automation.browsers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Driver {

    private URL driverLocation;
    private WebDriver driver;
    private String browser;
    private String serviceName;

    private static final Logger logger = LogManager.getLogger(Driver.class.getName());

    public Driver(String browser){
        logger.info("creating driver");
        this.browser = browser;
        try {
            switch (browser){
                case "chrome":{
                    System.setProperty("webdriver.chrome.driver", getDriverLocation());
                    driver = new ChromeDriver();
                    logger.info("chrome driver created");
                    break;
                }
                case "explorer":{
                    System.setProperty("webdriver.ie.driver", getDriverLocation());
                    driver = new InternetExplorerDriver();
                    logger.info("ie driver created");

                    break;
                }
                case "firefox":{
                    System.setProperty("webdriver.gecko.driver", getDriverLocation());
                    driver = new FirefoxDriver();
                    logger.info("firefox driver created");
                    break;
                }
            }
            driver.manage().window().maximize();
        } catch (Exception exception){
            logger.fatal("creating driver failed");
        }

    }

    private String getDriverLocation(){
        logger.info("getting driver path");
        switch (browser){
            case "chrome":{
                logger.info("getting chrome driver path");
                serviceName = "chromedriver.exe";
                break;
            }
            case "ie":{
                logger.info("getting ie driver path");
                serviceName = "IEDriverServer.exe";
                break;
            }
            case "firefox":{
                logger.info("getting firefox driver path");
                serviceName = "geckodriver.exe";
                break;
            }
        }
        driverLocation = Driver.class.getClassLoader().getResource(serviceName);
        return driverLocation.getPath();
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void kill() {
        try {
            Process p = Runtime.getRuntime().exec("taskkill /IM " + serviceName + " /F");
        } catch (IOException ioException){
            logger.error("couldnt kill " + serviceName + " driver task, please close it manually");
        }
    }

    public void enterUrl(String url){
        driver.get(url);
    }
}
