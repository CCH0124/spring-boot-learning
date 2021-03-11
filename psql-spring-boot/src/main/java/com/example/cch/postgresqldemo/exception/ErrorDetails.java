package com.example.cch.postgresqldemo.exception;

import java.util.Date;

public class ErrorDetails {
    private Date timsstamp;
    private String message;
    private String details;

    public Date getTimsstamp() {
        return timsstamp;
    }

    public void setTimsstamp(Date timsstamp) {
        this.timsstamp = timsstamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ErrorDetails(Date timsstamp, String message, String details) {
        this.timsstamp = timsstamp;
        this.message = message;
        this.details = details;
    }

    
    

}
