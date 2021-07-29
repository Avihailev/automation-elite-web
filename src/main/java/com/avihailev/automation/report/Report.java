package com.avihailev.automation.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report {

    private ExtentReports report;
    private ExtentTest test;

    public Report(String location, String title){
        this.report = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(location);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(title);
        sparkReporter.config().setReportName(title);
        this.report.attachReporter(sparkReporter);
    }

    public void startTest(String testName){
        test = report.createTest(testName);
    }

    public void endTest(){

    }

    public void reportStep(Status status, String body){
        test.log(status,body);
    }


    public void endReport(){
        this.report.flush();
    }

}
