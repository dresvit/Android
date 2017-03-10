package com.example.owner.lab5;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DynamicActivity extends AppCompatActivity {
    public static String DYNAMICACTION = "com.example.owner.lab5.dynamicreceiver";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        final DynamicReceiver dynamicReceiver = new DynamicReceiver();
        final Button regButton = (Button) findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regButton.getText().toString().equals("Register Broadcast")) {
                    regButton.setText("Unregister Broadcast");
                    IntentFilter dynamic_filter = new IntentFilter();
                    dynamic_filter.addAction(DYNAMICACTION);
                    registerReceiver(dynamicReceiver, dynamic_filter);
                } else {
                    regButton.setText("Register Broadcast");
                    unregisterReceiver(dynamicReceiver);
                }
            }
        });

        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regButton.getText().toString().equals("Unregister Broadcast")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "动态广播");
                    bundle.putString("content", editText.getText().toString());
                    bundle.putInt("image", R.mipmap.ic_dynamic);
                    Intent intent = new Intent(DYNAMICACTION);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
            }
        });
    }
}
