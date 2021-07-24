package com.avihailev.automation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TestSuite {

    private int suiteId;
    private String suitePlatform;
    private String suiteName;
    private List<Test> tests;
    private Settings settings;


}
