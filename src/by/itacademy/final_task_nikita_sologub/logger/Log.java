package by.itacademy.final_task_nikita_sologub.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Log {
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger("Log");
        }
        return logger;
    }
}