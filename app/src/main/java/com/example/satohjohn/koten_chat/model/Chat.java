package com.example.satohjohn.koten_chat.model;

import java.util.Date;

/**
 * Created by satohjohn on 16/04/15.
 */
public class Chat {
//    public long id;

    public String message;

    public Date publishAt;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Chat() {
    }

    public Chat(String message) {
        this.message = message;
        this.publishAt = new Date();
    }
}
