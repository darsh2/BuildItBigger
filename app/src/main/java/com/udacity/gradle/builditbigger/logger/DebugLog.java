package com.udacity.gradle.builditbigger.logger;

import android.util.Log;

import com.udacity.gradle.builditbigger.BuildConfig;

/**
 * Created by darshan on 6/3/17.
 */

public class DebugLog {
    private static final boolean isDebugBuild = BuildConfig.DEBUG;
    private static boolean loggingEnabled = true;

    private static final int requiredMethodIndex = 3;

    private static boolean isLoggingEnabled() {
        return isDebugBuild && loggingEnabled;
    }

    public static void setLoggingEnabled(boolean loggingEnabled) {
        DebugLog.loggingEnabled = loggingEnabled;
    }

    /**
     * Creates log entry with message being the method name it
     * was called from.
     */
    public static void logMethod() {
        if (!isLoggingEnabled()) {
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[requiredMethodIndex].getClassName();
        String methodName = stackTraceElements[requiredMethodIndex].getMethodName();
        Log.i(getTag(className), methodName);
    }

    /**
     * Log the particular message.
     */
    public static void logMessage(String message) {
        if (!isLoggingEnabled()) {
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[requiredMethodIndex].getClassName();
        Log.i(getTag(className), message);
    }

    /**
     * Returns tag based on tag name. Tag is of the following format:
     * "DL-" + EveryUpperCaseCharacterInClassName
     */
    private static String getTag(String className) {
        StringBuilder tag = new StringBuilder("DL-");
        for (char e : className.toCharArray()) {
            if (Character.isUpperCase(e)) {
                tag.append(e);
            }
        }
        return tag.toString();
    }
}
