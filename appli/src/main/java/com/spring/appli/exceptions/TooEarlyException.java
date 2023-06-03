package com.spring.appli.exceptions;

public class TooEarlyException extends Exception {

    public TooEarlyException() {
        super("Date needs to be seven days after the current day");
    }
}
