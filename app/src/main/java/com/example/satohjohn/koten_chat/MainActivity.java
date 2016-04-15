package com.example.satohjohn.koten_chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.satohjohn.koten_chat.model.Chat;
import com.example.satohjohn.koten_chat.model.FireBaseList;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Firebase myFirebaseRef;

    FireBaseList<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = new FireBaseList<>();

        Button send_button = (Button)findViewById(R.id.send_button);
        send_button.setOnClickListener(this);

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setOnKeyListener(this);

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.setBackgroundResource(R.drawable.base_1024);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://sweltering-fire-5633.firebaseio.com/");

        // firebaseにあるchatsを監視する
        myFirebaseRef.child("chats").addChildEventListener(new ChildEventListener() {

            // 追加された時
            // 初回起動時にも呼ばれる
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                String key = dataSnapshot.getKey();
                chats.add(chat, key, previousChildName);
                addRow(chat);
            }

            // 変更された時
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                String key = dataSnapshot.getKey();
                chats.change(chat, key);
            }

            // 削除された時
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                chats.remove(key);
            }

            // 順序が入れ替わった時
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                String key = dataSnapshot.getKey();
                chats.moved(chat, key, previousChildName);
            }

            @Override public void onCancelled(FirebaseError error) {
                System.out.print("error = [" + error + "]");
            }
        });


        // firebaseにつないでいるかどうかを調べる
        myFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupUsername();
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //EnterKeyが押されたかを判定
        if (event.getAction() == KeyEvent.ACTION_DOWN  && keyCode == KeyEvent.KEYCODE_ENTER) {

            //ソフトキーボードを閉じる
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            onClick(v);

            return true;
        }
        return false;
    }

    public void onClick(View v)
    {
        EditText comment = (EditText)findViewById(R.id.editText);

       if( comment.getText().toString().equals("") )
        {
            return;
        }

        // 保存するときにはそのままの文字列を入れる（後で見返せるように
        myFirebaseRef.child("chats").push().setValue(new Chat(getUserName(), comment.getText().toString()));

        // ここで追加するとローカルだけ一個多く追加されてしまう。
        comment.setText("");
    }

    private void addRow(Chat chat) {

        // TableLayoutのグループを取得
        ViewGroup vg = (ViewGroup)findViewById(R.id.chat_table);

        if (getUserName().equals(chat.userId)) {
            // 行を追加
            getLayoutInflater().inflate(R.layout.talbe_row_right, vg);
        } else {
            // 行を追加
            getLayoutInflater().inflate(R.layout.talbe_row, vg);
        }

        // 文字設定
        TableRow tr = (TableRow)vg.getChildAt(vg.getChildCount()-1);

        if (getUserName().equals(chat.userId)) {
            ((ImageView)(tr.findViewById(R.id.row_icon_image))).setImageResource(R.drawable.icon_01);
        } else {
            ((ImageView)(tr.findViewById(R.id.row_icon_image))).setImageResource(R.drawable.icon_02);
        }

        Log.d("aaa", "start");
        ChangeWord a = new ChangeWord();
        Log.d("aaa", "change start");
        String changeWord = a.Change(0, chat.message);

        // 表示の際に変換をかける
        ((TextView)(tr.findViewById(R.id.row_text))).setText(changeWord);

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void setupUsername() {
        String userName = getUserName();
        if (userName.isEmpty()) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            userName = "JavaUser" + r.nextInt(100000);
            this.getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE).edit().putString("username", userName).commit();
        }
    }

    private String getUserName() {
        SharedPreferences prefs = this.getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE);
        return prefs.getString("username", null) == null ? "" : prefs.getString("username", null);
    }
}
