package com.sber.javaschool.finalproject.json2http.utils;

public class Utils {
    public static boolean isNotValidName(String token) {
        for (int i = 0; i < token.length(); ++i) {
            char c = token.charAt(i);
            if (c > 255) {
                return true;
            }
        }

        return token.isEmpty();
    }

    public static boolean isNotValidValue(String token) {
        for (int i = 0; i < token.length(); ++i) {
            char c = token.charAt(i);
            if (c > 255) {
                return true;
            }
//            if (c != ' ' && c != '\t') {
//                return false;
//            }
        }

        return false;
    }
}
