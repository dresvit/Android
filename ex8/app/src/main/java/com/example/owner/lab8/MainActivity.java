package com.example.owner.lab8;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Cursor cursor;
    private myDB DB;
    private ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new myDB(MainActivity.this, "mydatabase.db", null, 1);
        userList = (ListView) findViewById(R.id.userList);
        updateUserList();

        Button addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View layout = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                TextView name_modify = (TextView) layout.findViewById(R.id.name_modify);
                final EditText birth_modify = (EditText) layout.findViewById(R.id.birth_modify);
                final EditText gift_modify = (EditText) layout.findViewById(R.id.gift_modify);
                TextView phone = (TextView) layout.findViewById(R.id.phone);

                cursor = DB.getCursor();
                cursor.moveToPosition(position);
                final String currentName = cursor.getString(cursor.getColumnIndex("name"));
                name_modify.setText(currentName);
                birth_modify.setText(cursor.getString(cursor.getColumnIndex("birth")));
                gift_modify.setText(cursor.getString(cursor.getColumnIndex("gift")));
                phone.setText(getPhone(currentName));

                builder.setTitle("o(*￣▽￣*)ブ");
                builder.setView(layout);
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.update(currentName, birth_modify.getText().toString(), gift_modify.getText().toString());
                        updateUserList();
                    }
                }).setNegativeButton("放弃修改", null);
                builder.create().show();
            }
        });

        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                        setTitle("是否删除?").
                        setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).
                        setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cursor = DB.getCursor();
                                cursor.moveToPosition(position);
                                String currentName = cursor.getString(cursor.getColumnIndex("name"));
                                DB.delete(currentName);
                                updateUserList();
                            }
                        }).
                        create();
                alertDialog.show();
                return true;
            }
        });
    }

    public void updateUserList() {
        cursor = DB.getCursor();
        if (cursor != null && cursor.getCount() >= 0) {
            final ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cursor,
                    new String[]{"name", "birth", "gift"}, new int[]{R.id.name, R.id.birth, R.id.gift}, 0);
            userList.setAdapter(adapter);
        }
    }

    public String getPhone(String name) {
        Cursor cursor = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        String result = "无";
        if (cursor == null || cursor.getCount() == 0 || !cursor.moveToFirst()) {
            return result;
        }
        int resultCounts = cursor.getCount();
        String currentName;
        for (int i = 0; i < resultCounts; i++) {
            currentName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            if (currentName.equals(name)) {
                String phoneNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                if (!TextUtils.isEmpty(phoneNum)) result = phoneNum;
                break;
            }
            cursor.moveToNext();
        }
        return result;
    }
}
