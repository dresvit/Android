package com.example.owner.experimenttwo_2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        final TextInputLayout username = (TextInputLayout) findViewById(R.id.more_username);
        final TextInputLayout password = (TextInputLayout) findViewById(R.id.more_password);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final View rootView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString();
                String pwd = password.getEditText().getText().toString();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
                    if (TextUtils.isEmpty(user)) {
                        username.setErrorEnabled(true);
                        username.setError("用户名不能为空");
                    } else username.setErrorEnabled(false);
                    if (TextUtils.isEmpty(pwd)) {
                        password.setErrorEnabled(true);
                        password.setError("密码不能为空");
                    } else password.setErrorEnabled(false);
                } else {
                    username.setErrorEnabled(false);
                    password.setErrorEnabled(false);
                    if (user.equals(getString(R.string.user_record)) && pwd.equals(getString(R.string.pwd_record))) {
                        Snackbar.make(rootView, "登录成功", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    } else {
                        Snackbar.make(rootView, "登录失败", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                Snackbar.make(rootView, rb.getText().toString() + "身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                        .setAction("按钮", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .setDuration(5000)
                        .show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                Snackbar.make(rootView, rb.getText().toString() + "身份被选中", Snackbar.LENGTH_SHORT)
                        .setAction("按钮", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .setDuration(5000)
                        .show();
            }
        });
    }
}
