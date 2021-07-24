package com.avihailev.automation.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActionType {
    @JsonProperty("click")
    Click,
    @JsonProperty("set")
    Set,
    @JsonProperty("compare")
    CompareText
}
