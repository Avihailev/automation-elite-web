package com.avihailev.automation.common;

import com.avihailev.automation.TestStep;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;

public class CommonActions {

    private static final Logger logger = LogManager.getLogger(CommonActions.class.getName());

    public static String printScreen(WebDriver driver, TestStep step){
        logger.info("taking screenshot");
        if (checkFolder(step.getSettings().getReportLocation())) {
            try {
                String timeStamp = String.valueOf(System.currentTimeMillis());
                String fileName = timeStamp+"_"+step.getStepId()+"_"+step.getStepName()+"-screenshot.png";
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destFile = new File(step.getSettings().getReportLocation()+fileName);
                FileUtils.copyFile(screenshot, destFile);
                return destFile.getAbsolutePath();
            } catch (WebDriverException webDriverException) {
                logger.error("failed to take screenshot");
            } catch (IOException ioException) {
                logger.error("failed to save screenshot file");
            }
        }
        return null;
    }

    public static String printScreenBASE64(WebDriver driver){
        logger.info("taking screenshot base 64");
            try {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            } catch (WebDriverException webDriverException) {
                logger.error("failed to take screenshot");
            }
        return null;
    }

    private static boolean checkFolder(String location){
        File file = new File(location);
        if (!file.isDirectory()){
            logger.error("report location entered is a file and not a folder, please pass a folder location -> screenshot not saved");
            return false;
        }
        if (!file.exists()){
            logger.info("folder not exist creating folder");
            if (file.mkdir()){
                logger.info("folder created successfully");
                return true;
            } else {
                logger.info("folder creation failed, screenshot not saved");
                return false;
            }
        } else {
            return true;
        }
    }

    public static void killDrivers() {
        try {
            String[] serviceNames = {"IEDriverServer.exe","chromedriver.exe","geckodriver.exe"};
            for (String service : serviceNames){
                Process p = Runtime.getRuntime().exec("taskkill /IM " + service + " /F");
            }
        } catch (IOException ioException){
            logger.error("couldnt kill all drivers task, please close it manually");
        }
    }
}
