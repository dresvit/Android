package com.example.owner.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button okButton = (Button) findViewById(R.id.okButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        final EditText newPwd = (EditText) findViewById(R.id.newPwd);
        final EditText confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        final SharedPreferences sharedPreferences = getSharedPreferences("SaveSettings", MODE_PRIVATE);
        final String validPwd = sharedPreferences.getString("pwd", null);
        final boolean flag;

        if (validPwd == null) {
            flag = false;
        } else {
            flag = true;
            newPwd.setVisibility(View.INVISIBLE);
            confirmPwd.setHint("Password");
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    String confirmPwdStr = confirmPwd.getText().toString();
                    if (confirmPwdStr.equals(validPwd)) {
                        startActivity(new Intent(MainActivity.this, FileEditorActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String newPwdStr = newPwd.getText().toString();
                    String confirmPwdStr = confirmPwd.getText().toString();
                    if (TextUtils.isEmpty(newPwdStr)) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (newPwdStr.equals(confirmPwdStr)) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("pwd", newPwdStr);
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, FileEditorActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Password Mismatch.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPwd.setText("");
                confirmPwd.setText("");
            }
        });
    }
}
