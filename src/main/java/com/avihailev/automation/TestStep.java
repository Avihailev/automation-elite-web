package com.avihailev.automation;

import com.avihailev.automation.types.ActionType;
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
    private ActionType keywordAction;
    private String actionValue;
    private boolean mandatory;
    private Settings settings;

}
