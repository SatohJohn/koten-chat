package com.example.satohjohn.koten_chat;

import com.example.satohjohn.koten_chat.ChangeWordInfo;

import java.util.List;

/**
 * Created by 0FR114036 on 2016/04/15.
 */
public class ChangeWord {
    List<ChangeWordInfo> kogoList;
    ChangeWord()
    {
        kogoList.clear();

        kogoList.add(new ChangeWordInfo("おもむきがある", "おかし"));
    }

}
