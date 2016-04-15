package com.example.satohjohn.koten_chat.model;

import java.util.Date;

/**
 * Created by satohjohn on 16/04/15.
 */
public class Chat {
//    public long id;

    public String message;

    public String publishAt;

    public Chat(String message, String publishAt) {
        this.message = message;
        this.publishAt = publishAt;
    }
}
