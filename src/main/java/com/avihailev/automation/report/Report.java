package com.avihailev.automation.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.avihailev.automation.Action;
import com.avihailev.automation.Element;
import com.avihailev.automation.TestStep;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Report {

    private ExtentReports report;
    private ExtentTest test;

    public Report(String location, String title){
        this.report = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileLocation(location));
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(title);
        sparkReporter.config().setReportName(title);
        sparkReporter.config().thumbnailForBase64(true);
        this.report.attachReporter(sparkReporter);
    }

    public void report(Action action){
        TestStep step = action.getStep();
        if (!action.isPassed()){
            ArrayList<String> headersList = new ArrayList<>(Arrays.asList("ELEMENT NAME", "ACTION TYPE", "ACTION VALUE", "DESCRIPTION"));
            ArrayList<String> tableBody = new ArrayList<>(Arrays.asList(step.getStepName(), step.getKeywordAction().toString(), step.getActionValue(), action.getReportMessage()));
            reportElement(Status.FAIL, headersList, tableBody, action.getScreenShot());
        } else {
            ArrayList<String> headersList = new ArrayList<>(Arrays.asList("ELEMENT NAME", "FIND BY", "KEYWORD VALUE", "ACTION TYPE", "ACTION VALUE", "DESCRIPTION"));
            ArrayList<String> tableBody = new ArrayList<>(Arrays.asList(step.getStepName(), step.getFindKeywordBy(), step.getKeywordValue(), step.getKeywordAction().toString(), step.getActionValue(), action.getReportMessage()));
            reportElement(Status.PASS, headersList, tableBody, action.getScreenShot());
        }
    }

    public void report(Element element){
        if (!element.isPassed()){
            TestStep step = element.getStep();
            ArrayList<String> headersList = new ArrayList<>(Arrays.asList("ELEMENT NAME", "FIND BY", "KEYWORD VALUE", "DESCRIPTION"));
            ArrayList<String> tableBody = new ArrayList<>(Arrays.asList(step.getStepName(), step.getFindKeywordBy(), step.getKeywordValue(), element.getReportMessage()));
            reportElement(Status.FAIL, headersList, tableBody, element.getScreenShot());
        }
    }

    public void startTest(String testName){
        test = report.createTest(testName);
    }

    public void endTest(){

    }

    private void reportStep(Status status, String body){
        test.log(status,body);
    }

    private void reportStep(Status status, String body, String screenShotBase64){
        test.log(status,body,MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
    }


    public void endReport(){
        this.report.flush();
    }

    private void reportElement(Status status, ArrayList<String> headersList, ArrayList<String> tableBody, String screenShot){
        HTMLBuilder htmlBuilder = new HTMLBuilder();
        List<List<String>> tableList = new ArrayList<>();
        tableList.add(tableBody);
        htmlBuilder.buildHTMLTable("", headersList, tableList);
        reportStep(status,htmlBuilder.getTable(), screenShot);
    }

    private String fileLocation(String location){
        String tempLocation = "c:\\temp\\";
        if (!location.endsWith("\\")){
            location += "\\";
        }
        File file = new File(location);
        if (!file.isDirectory() || !file.exists()){
            return tempLocation + getFileName();
        } else {
            return location + getFileName();
        }
    }

    private String getFileName(){
        return "Automation-Report-" + System.currentTimeMillis() + ".html";
    }

}
