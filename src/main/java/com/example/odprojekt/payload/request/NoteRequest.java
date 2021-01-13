package com.example.odprojekt.payload.request;

public class NoteRequest {


    private String body;

    public NoteRequest(String body) {
        this.body = body;
    }

    public NoteRequest() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
