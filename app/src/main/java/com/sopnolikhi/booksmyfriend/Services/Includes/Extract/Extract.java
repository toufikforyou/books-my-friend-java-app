package com.sopnolikhi.booksmyfriend.Services.Includes.Extract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {
    public static int ExtractDigit(String message) {
        Pattern pattern = Pattern.compile("(\\d+):");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String errorCodeString = matcher.group(1);
            assert errorCodeString != null;
            return Integer.parseInt(errorCodeString);
        } else {
            // Handle the case where no numeric code is found
            return -1; // or throw an exception
        }
    }

    public static String ExtractMessage(String errorMessage) {
        int colonIndex = errorMessage.indexOf(':');
        if (colonIndex >= 0 && colonIndex + 1 < errorMessage.length()) {
            return errorMessage.substring(colonIndex + 2);
        } else {
            // Handle the case where no colon is found or it's at the end of the string
            return errorMessage;
        }
    }
}
