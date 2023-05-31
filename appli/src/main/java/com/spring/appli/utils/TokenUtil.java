package com.spring.appli.utils;

import com.spring.appli.exceptions.BadTokenException;

public class TokenUtil {

    public static final int ID = 0;
    public static final int ROLE = 1;

    public static String parseToken(String token, int index) throws BadTokenException {
        // Not a real token but wasn't the objective of the project
        if (token == null || index > 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        String[] items = token.split("\\+");

        if (items.length != 2) {
            throw new BadTokenException();
        }
        return items[index];
    }

}
