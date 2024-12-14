package com.oyameen.apigateway.exception;

public class ErrorModel {
    private long timeStamp;
    private int status;
    private String error;
    private String message;

    public ErrorModel() {
    }

    public ErrorModel(long timeStamp, int status, String error, String message) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorModel{" +
                "timeStamp=" + timeStamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
