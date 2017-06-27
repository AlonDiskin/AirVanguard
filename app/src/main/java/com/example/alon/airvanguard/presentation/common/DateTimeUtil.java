package com.example.alon.airvanguard.presentation.common;

import android.text.format.DateFormat;

/**
 * Utility class for date and time converting
 * methods.
 */

public class DateTimeUtil {

    // String.valueOf(DateFormat.format("MMMM d HH:mm",distressCall.getTimeStamp()))

    /**
     * Converts the given time {@code stamp} to a
     * formated string according to the given time {@code format}.
     *
     * @param stamp time stamp in milliseconds.
     * @param format date format.
     * @returnformated date string.
     */
    public static String convert(long stamp,String format) {
        return String.valueOf(DateFormat.format(format,stamp));
    }
}
