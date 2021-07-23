package com.avihailev.automation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Test {

    private int testId;
    private String testName;
    private String testBrowser;
    private List<TestStep> steps;

}
