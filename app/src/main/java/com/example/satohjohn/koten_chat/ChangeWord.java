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

        kogoList.add(new ChangeWordInfo("私の彼氏とってもいいかげんなの","わがめづの方、げにおろかなり"));
        kogoList.add(new ChangeWordInfo("ねこがかわいい","ねこまうるわしきかな"));
        kogoList.add(new ChangeWordInfo("おなかすいた","おなかすゐき"));
        kogoList.add(new ChangeWordInfo("遊びにいこう","明日遊びにいかむ"));
        kogoList.add(new ChangeWordInfo("いいかげん","おろかなり"));
        kogoList.add(new ChangeWordInfo("いやだ"," いとほし"));
        kogoList.add(new ChangeWordInfo("かわいいね","うるわしきかな"));
        kogoList.add(new ChangeWordInfo("今っぽい","いまめかし"));
        kogoList.add(new ChangeWordInfo("さっきの","ありつる"));
        kogoList.add(new ChangeWordInfo("ざんねんだ","ほいなし"));
        kogoList.add(new ChangeWordInfo("じつに","げに"));
        kogoList.add(new ChangeWordInfo("それぞれ","おのがじし"));
        kogoList.add(new ChangeWordInfo("たいせつだ","やむごとなし"));
        kogoList.add(new ChangeWordInfo("つまらない","あぢきなし"));
        kogoList.add(new ChangeWordInfo("ふかいだ","いぶせし"));
        kogoList.add(new ChangeWordInfo("ほんとうに","げに,いと"));
        kogoList.add(new ChangeWordInfo("まちどおしい","こころもとなし"));
        kogoList.add(new ChangeWordInfo("むだだ","いたづらなり"));
        kogoList.add(new ChangeWordInfo("やっと","やうやう"));
        kogoList.add(new ChangeWordInfo("わたし","われ"));
        kogoList.add(new ChangeWordInfo("あいする","めづ "));
        kogoList.add(new ChangeWordInfo("ねこ","ねこま"));
        kogoList.add(new ChangeWordInfo("おもむきがある", "おかし"));
        kogoList.add(new ChangeWordInfo("おもしろい", "おかし"));
        kogoList.add(new ChangeWordInfo("まじで", "いと"));
        kogoList.add(new ChangeWordInfo("とても", "いと"));
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
