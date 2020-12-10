package io.github.techdweebgaming.jdacommander.api.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static void log(String level, String message) {
        System.out.println(String.format("[%s][%s] %s", dateFormat.format(new Date()), level.toUpperCase(), message));
    }

    public static void logError(String message) {
        log("ERROR", message);
    }

}
