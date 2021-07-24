package com.avihailev.automation;

import com.avihailev.automation.types.ActionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Actions {

    private Element element;

    private static final Logger logger = LogManager.getLogger(Actions.class.getName());

    public Actions(Element element, TestStep step){
        logger.info("deciding what action to take");
        this.element = element;
    }
}
