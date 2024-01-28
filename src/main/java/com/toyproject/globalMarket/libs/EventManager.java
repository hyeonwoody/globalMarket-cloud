package com.toyproject.globalMarket.libs;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EventManager {
    private static final int MAX_LOG_MESSAGE = 1024;
    private static final Logger logger = Logger.getLogger(EventManager.class.getName());
    private static void output(int level, String session, String func, int outputIndex, String message) {
        Level logLevel;
        switch (level) {
            case 0:
                logLevel = Level.SEVERE;
                break;
            case 1:
                logLevel = Level.WARNING;
                break;
            case 2:
                logLevel = Level.INFO;
                break;
            case 3:
                logLevel = Level.CONFIG;
                break;
            case 4:
                logLevel = Level.FINE;
                break;
            case 5:
                logLevel = Level.FINER;
                break;
            case 6:
                logLevel = Level.FINEST;
                break;
            default:
                logLevel = Level.INFO;
                break;
        }

        logger.log(logLevel, "[{0}][{1}][{2}]: {3}", new Object[]{session, func, outputIndex, message});
    }
    public static void logOutput(int level, String session, String func, int outputIndex, String format, Object... args) {
        String message = MessageFormat.format(format, args);
        output(level, session, func, outputIndex, message);
    }

}
