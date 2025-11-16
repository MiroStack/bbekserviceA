package com.bbek.BbekServiceA.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionLogger {

    public void logError(String className, Exception e){
        Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
        logger.error("An error occurred while processing request", e);
    }
}
