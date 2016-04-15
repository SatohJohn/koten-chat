package com.example.satohjohn.koten_chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.satohjohn.koten_chat.model.Chat;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Firebase myFirebaseRef;
    ArrayList<Chat> chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = new ArrayList<Chat>();

        Button send_button = (Button)findViewById(R.id.send_button);
        send_button.setOnClickListener(this);

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setOnKeyListener(this);

        Bitmap bm = null;
        try {
            InputStream is = getResources().getAssets().open("base_1024.png");
            bm = BitmapFactory.decodeStream(is);
            //bm = bm.createScaledBitmap(bm, 256, 256, false);
        }
        catch (IOException e) {
            Log.d("Assets","Error");
        }
        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);

        BitmapDrawable ob = new BitmapDrawable(getResources(), bm);
        scrollView.setBackground(ob);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase("https://sweltering-fire-5633.firebaseio.com/");

        myFirebaseRef.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds:  snapshot.getChildren()) {
                    showText(ds.getValue().toString());
//                    showText(value.message);
                }
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
//                if (snapshot.getValue() != null) {
////                    HashMap<String, Object> data = snapshot.getValue();
//                    LinkedHashMap<Long, ArrayList<Chat>> value = (LinkedHashMap<Long, ArrayList<Chat>>) snapshot.getValue(new LinkedHashMap<Long, ArrayList<Chat>>().getClass());
//                    showText(value.toString());
//                }
            }
            @Override public void onCancelled(FirebaseError error) {
                System.out.print("error = [" + error + "]");
            }
        });

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

        Log.d("aaa", "start");
        ChangeWord a = new ChangeWord();
        Log.d("aaa", "change start");
        String changeWord =  a.Change(0, comment.getText().toString());

        showText(changeWord);
        comment.setText("");

        myFirebaseRef.child("chats").push().setValue(changeWord);
    }

    private void showText(String text) {

        Bitmap bm = null;

        // TableLayoutのグループを取得
        ViewGroup vg = (ViewGroup)findViewById(R.id.chat_table);

        if( vg.getChildCount() %2 == 0 ) {
            try {
                InputStream is = getResources().getAssets().open("icon_01.png");
                bm = BitmapFactory.decodeStream(is);
                bm = bm.createScaledBitmap(bm, 256, 256, false);
            }
            catch (IOException e) {
                Log.d("Assets","Error");
            }

            // 行を追加
            getLayoutInflater().inflate(R.layout.talbe_row, vg);

            // 文字設定
            TableRow tr = (TableRow)vg.getChildAt(vg.getChildCount()-1);
            ((ImageView)(tr.getChildAt(0))).setImageBitmap(bm);
            ((TextView)(tr.getChildAt(1))).setText(text);
        }
        else {
            try {
                InputStream is = getResources().getAssets().open("icon_02.png");
                bm = BitmapFactory.decodeStream(is);
                bm = bm.createScaledBitmap(bm, 256, 256, false);
            }
            catch (IOException e) {
                Log.d("Assets","Error");
            }

            // 行を追加
            getLayoutInflater().inflate(R.layout.talbe_row_right, vg);

            // 文字設定
            TableRow tr = (TableRow)vg.getChildAt(vg.getChildCount()-1);
            ((ImageView)(tr.getChildAt(1))).setImageBitmap(bm);
            ((TextView)(tr.getChildAt(0))).setText(text);
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
}
