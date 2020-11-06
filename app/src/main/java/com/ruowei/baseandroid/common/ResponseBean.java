package com.ruowei.baseandroid.common;


public class ResponseBean {


    public Object data;

    public String message;

    public int status;


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "errCd=" + status +
                ", errMsg='" + message + '\'' +
                ", result=" + data +
                '}';
    }
}