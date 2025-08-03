package com.sopnolikhi.booksmyfriend.Services.Validations;

public class Validate {
    public static boolean isUserNameEmailPhone(String username) {
        // ^(?:(?!\d+$)[a-zA-Z0-9._]{5,15}|[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}|\+?(?:8801|01|1)[1-9][0-9]{8})$
        return username.matches("^(?:(?!\\d+$)[a-zA-Z\\d._]{5,20}|[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}|\\+?(?:8801|01|1)[1-9]\\d{8})$");
    }

    public static boolean isEmailOrMobileValid(String input) {
        // Define a regular expression to validate email or mobile number
        return input.matches("^(?:[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}|\\+?(?:8801|01|1)[1-9]\\d{8})$");
    }


    public static boolean isPasswordValid(String password) {
        // Check if the input meets the criteria
        return password != null && password.matches("^(?=.*[a-zA-Z0-9@#$%^&+=!*? (/':;|_){}<>]*$)[a-zA-Z0-9@#$%^&+=!*? (/':;|_){}<>]{8,}$");
    }

    public static boolean isNameValid(String name) {
        // Check if the input meets the criteria
        return name != null && name.length() >= 3 && // Minimum length
                name.length() <= 50 // Maximum length (adjust as needed)
                ;
    }

    public static boolean confirmPassword(String password, String confirm) {
        return password.equals(confirm);
    }
}
