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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static java.util.Locale.getDefault;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
