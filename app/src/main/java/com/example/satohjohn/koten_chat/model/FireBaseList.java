package com.example.satohjohn.koten_chat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satohjohn on 16/04/15.
 */
public class FireBaseList<T> {
    List<T> chats = new ArrayList<>();
    List<String> keys = new ArrayList<>();

    public FireBaseList() {
    }

    // 順番がまちまちになる可能性があるので、きちんと整合させる
    public void add(T chat, String key, String prevKey) {
        if (prevKey == null) {
            chats.add(0, chat);
            keys.add(0, key);
        } else {
            int i = keys.indexOf(prevKey);
            int nextIndex = i + 1;
            if (nextIndex == chats.size()) {
                chats.add(chat);
                keys.add(key);
            } else {
                chats.add(nextIndex, chat);
                keys.add(nextIndex, key);
            }
        }
    }

    public void change(T chat, String key) {
        int i = keys.indexOf(key);
        if (0 < i || chats.size() <= i) {
            return;
        }
        chats.set(i, chat);
    }

    public void remove(String key) {
        int i = keys.indexOf(key);
        if (0 < i || chats.size() <= i) {
            return;
        }
        keys.remove(i);
        chats.remove(i);
    }

    public void moved(T chat, String key, String prevKey) {
        int index = keys.indexOf(key);
        chats.remove(index);
        keys.remove(index);
        add(chat, key, prevKey);
    }

    public T get(int i) {
        return chats.get(i);
    }

    public int size() {
        return chats.size();
    }

    public void clear() {
        chats.clear();
        keys.clear();
    }
}
