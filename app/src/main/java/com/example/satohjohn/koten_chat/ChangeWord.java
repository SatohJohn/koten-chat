package com.example.satohjohn.koten_chat;

import android.util.Log;

import com.example.satohjohn.koten_chat.ChangeWordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0FR114036 on 2016/04/15.
 */
public class ChangeWord {
    List<ChangeWordInfo> kogoList = new ArrayList<>();
    ChangeWord()
    {
        kogoList.clear();

        kogoList.add(new ChangeWordInfo("おもむきがある", "おかし"));
    }

    String Change(int no, String word)
    {
        Log.d("aaa", "Start : " + word);
        for (ChangeWordInfo item : kogoList)
        {
            Log.d("aaa", item.now + " : " + word + " : " + (item.now == word));
            if (item.now == word)
            {
                return item.after;
            }
        }
        return word;
    }
}
