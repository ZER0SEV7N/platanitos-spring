package com.platanitos.springplatanitos.models.payload;

public class Response <T>{
    public Boolean success;
    public String message;
    public T data;

    public Response(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() { return success; }

    public String getMessage() { return message; }

    public T getData() { return data; }
}
