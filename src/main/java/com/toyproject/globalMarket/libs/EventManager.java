package com.toyproject.globalMarket.libs;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EventManager {
    private static final int MAX_LOG_MESSAGE = 1024;


    public enum LOG_LEVEL{
        ERROR,
        INFO,
        WARNING,
        SKIPABLE,
        DEBUG
    }
    private static void LogExecute(LOG_LEVEL level, String session, String func, int outputIndex, String time, String buffer) {
        if (session == null || session.isEmpty()) {
            session = "NoName";
        }

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append(time)
                .append("|")
                .append(level.name())
                .append("|")
                .append(session)
                .append(": ")
                .append(outputIndex)
                .append(" | ")
                .append(func)
                .append(" | ")
                .append(buffer);

        System.out.println(logBuilder.toString());
    }

    private static void Output(LOG_LEVEL level, String session, String func, int outputIndex, String message) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String time = dateFormat.format(now);
        LogExecute(level, session, func, outputIndex, time, message);
    }
    public static void LogOutput(LOG_LEVEL level, String session, String func, int outputIndex, String format, Object... args) {
        String message = MessageFormat.format(format, args);
        Output(level, session, func, outputIndex, message);
    }

}
