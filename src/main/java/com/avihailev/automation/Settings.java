package com.avihailev.automation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Settings {

    private String reportLocation;
    private boolean highlightElement;
    private int waitBetween;
    //private int threadNumber;
}
