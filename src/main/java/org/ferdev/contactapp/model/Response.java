package org.ferdev.contactapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int code;
    private String message;
    private List<String> errorList;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, List<String> errorList) {
        this.code = code;
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
