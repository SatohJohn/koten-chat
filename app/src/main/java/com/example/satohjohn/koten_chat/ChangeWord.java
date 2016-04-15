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
        kogoList.add(new ChangeWordInfo("おもしろい", "おかし"));
        kogoList.add(new ChangeWordInfo("まじで", "いと"));
        kogoList.add(new ChangeWordInfo("とても", "いと"));
    }

    String Change(int no, String word)
    {
        Log.d("aaa", "Start : " + word);
        String nextWord = word;
        String changeWord = "";
        while(nextWord.isEmpty() == false) {
            Log.d("aaa", nextWord + " : " + changeWord);
            boolean isFind = false;
            for (ChangeWordInfo item : kogoList) {
                // 文字列が見つかった
                if (nextWord.indexOf(item.now) == 0) {
                    changeWord += item.after;
                    nextWord = nextWord.substring(item.now.length());
                    isFind = true;
                    break;
                }
            }
            if (isFind == false) {
                changeWord += nextWord.substring(0,1);
                nextWord = nextWord.substring(1);
            }
        }
        return changeWord;
    }
}
