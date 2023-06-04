package com.spring.appli.exceptions;

public class AccessDeniedException extends Exception {

    public AccessDeniedException() {
        super("Role doesn't allow to use this service");
    }
}
