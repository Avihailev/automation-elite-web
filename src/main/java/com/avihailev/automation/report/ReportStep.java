package com.avihailev.automation.report;

import com.aventstack.extentreports.Status;

public class ReportStep {

    private int stepNumber;
    private Status status;
    private String body;

    public ReportStep(int stepNumber, Status status, String body){
        this.stepNumber = stepNumber;
        this.status = status;
        this.body = body;
    }
}
