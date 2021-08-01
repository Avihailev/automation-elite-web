package com.avihailev.automation.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.shape.MoveTo;

public enum ActionType {
    @JsonProperty("click")
    Click,
    @JsonProperty("set")
    Set,
    @JsonProperty("compare")
    CompareText,
    @JsonProperty("double click")
    DoubleClick,
    @JsonProperty("wait")
    Wait,
    @JsonProperty("context click")
    ContextClick,
    @JsonProperty("move to element")
    MoveToElement
}
