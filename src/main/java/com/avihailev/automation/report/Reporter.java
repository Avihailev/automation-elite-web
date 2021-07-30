package com.avihailev.automation.report;

import com.avihailev.automation.TestStep;
import com.avihailev.automation.common.CommonActions;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

@Getter
@Setter
public class Reporter {

    private String reportMessage;
    private boolean passed;
    private String screenShot;

    public Reporter(){

    }

    public void printScreen(WebDriver driver){
        screenShot = CommonActions.printScreenBASE64(driver);
    }

}
