package com.example.satohjohn.koten_chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Firebase myFirebaseRef;
    ArrayList<Chat> chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = new ArrayList<Chat>();

        Button send_button = (Button)findViewById(R.id.send_button);
        send_button.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        EditText comment = (EditText)findViewById(R.id.editText);

        if( comment.getText().equals("") )
        {
            return;
        }

        // TableLayoutのグループを取得
        ViewGroup vg = (ViewGroup)findViewById(R.id.chat_table);

        // 行を追加
        getLayoutInflater().inflate(R.layout.talbe_row, vg);
        // 文字設定
        TableRow tr = (TableRow)vg.getChildAt(vg.getChildCount()-1);

        Bitmap bm = null;
        try {
            InputStream is = getResources().getAssets().open("icon_01.jpg");
            bm = BitmapFactory.decodeStream(is);
        }
        catch (IOException e) {
            Log.d("Assets","Error");
        }
        ((ImageView)(tr.getChildAt(0))).setImageBitmap(bm);
        ((TextView)(tr.getChildAt(1))).setText(comment.getText());
        comment.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        Button button = (Button) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFirebaseRef.child("chats").setValue(new Chat("hogehoge", new Date().toString()));
            }
        });
    }
    private void showText(String text) {
        TextView te = (TextView) findViewById(R.id.text);
        if (te != null) {
            te.setText(text);
        }
    }
}
