package com.example.owner.lab8;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button okButton = (Button) findViewById(R.id.okButton);
        final EditText nametext = (EditText) findViewById(R.id.name_text);
        final EditText birthtext = (EditText) findViewById(R.id.birth_text);
        final EditText gifttext = (EditText) findViewById(R.id.gift_text);
        final myDB DB = new myDB(AddActivity.this, "mydatabase.db", null, 1);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestr = nametext.getText().toString();
                if (TextUtils.isEmpty(namestr)) {
                    Toast.makeText(AddActivity.this, "名字为空，请完善", Toast.LENGTH_SHORT).show();
                } else {
                    if (DB.findName(namestr)) {
                        Toast.makeText(AddActivity.this, "名字重复啦，请核查", Toast.LENGTH_SHORT).show();
                    } else {
                        String birthstr = birthtext.getText().toString();
                        String giftstr = gifttext.getText().toString();
                        DB.insert(namestr, birthstr, giftstr);
                        startActivity(new Intent(AddActivity.this, MainActivity.class));
                    }
                }
            }
        });
    }
}
