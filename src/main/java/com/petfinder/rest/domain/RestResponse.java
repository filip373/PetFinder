package com.petfinder.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {

    private String message = "";

    private int responseCode = 0;

    private String status;

    private Object requestObject = null;

    public RestResponse() {}

    public RestResponse(String status) {
        this.status = status;
    }

    public RestResponse(int responseCode, String status) {
        this.responseCode = responseCode;
        this.status = status;
    }

    public RestResponse(String message, int responseCode, String status) {
        this.message = message;
        this.responseCode = responseCode;
        this.status = status;
    }

    public RestResponse(String message, int responseCode, String status,
                        Object requestObject) {
        this.message = message;
        this.responseCode = responseCode;
        this.status = status;
        this.requestObject = requestObject;
    }

    public RestResponse(int responseCode, String status, Object requestObject) {
        this.responseCode = responseCode;
        this.status = status;
        this.requestObject = requestObject;
    }

    public RestResponse(String status, Object requestObject) {
        this.status = status;
        this.requestObject = requestObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }
}
