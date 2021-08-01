package com.avihailev.automation;

import com.avihailev.automation.browsers.Driver;
import com.avihailev.automation.report.Report;

public class TestThreadRunner implements Runnable {

    private Test test;
    private Settings settings;

    public TestThreadRunner(Test test, Settings settings){
        this.test = test;
        this.settings = settings;
    }

    @Override
    public void run() {
        //ReportTest reportTest = new ReportTest(test.getTestName());
        Report report = new Report(settings.getReportLocation(), test.getTestName() + " - Automation Report");
        report.startTest(test.getTestName());
        Driver driver = new Driver(test.getTestBrowser());
        driver.enterUrl(test.getUrl());
        for (TestStep step : test.getSteps()){
            step.setSettings(settings);
            Element element = new Element(driver,step);
            if (element.find()){
                Action action = new Action(element,step);
                if (action.action()) {
                    report.report(action);
                } else {
                    report.report(action);
                    break;
                }
            } else if (step.isMandatory()){
                report.report(element);
                break;
            }
        }
        report.endReport();
    }

}
