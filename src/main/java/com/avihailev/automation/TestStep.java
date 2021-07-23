package com.avihailev.automation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestStep {

    private int stepId;
    private String stepName;
    private String findKeywordBy;
    private String keywordValue;
    private String keywordAction;
    private String actionValue;

}
