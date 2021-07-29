package com.avihailev.automation.report;

import com.aventstack.extentreports.model.Report;


import java.util.ArrayList;
import java.util.List;

public class ReportTest {

    private List<ReportStep> reportSteps;

    public ReportTest(){
        this.reportSteps = new ArrayList<>();

    }

    public void addStepReport(ReportStep reportStep){
        this.reportSteps.add(reportStep);
    }
}
