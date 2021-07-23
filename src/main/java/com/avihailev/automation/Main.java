package com.avihailev.automation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            InputStream testStream = Main.class.getClassLoader().getResourceAsStream("tests.json");
            TestSuite suite = objectMapper.readValue(testStream, TestSuite.class);
            TestFactory testFactory = new TestFactory(suite);
            testFactory.run();
        } catch (Exception exception){
            logger.fatal("Failed in main operation of reading tests json file with this error");
            logger.fatal(exception.getMessage());
            System.exit(-1);
        }

        //System.exit(0);

    }
}
