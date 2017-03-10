package com.example.owner.lab3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = this.getIntent().getExtras();
        ((TextView)findViewById(R.id.detail_name)).setText((String)bundle.get("name"));
        ((TextView)findViewById(R.id.detail_phoneNum)).setText((String)bundle.get("phoneNum"));
        ((TextView)findViewById(R.id.detail_type)).setText((String)bundle.get("type"));
        ((TextView)findViewById(R.id.detail_location)).setText((String)bundle.get("location"));
        ((RelativeLayout)findViewById(R.id.topPanel)).setBackgroundColor(Color.parseColor((String)bundle.get("color")));

        List<Map<String, Object>> data = new ArrayList<>();
        String [] option = new String[] {"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};

        for (int i = 0; i < 4; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("option", option[i]);
            data.add(temp);
        }

        ListView menu = (ListView) findViewById(R.id.menu);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item2,
                new String[] {"option"}, new int[] {R.id.option});
        menu.setAdapter(simpleAdapter);

        final ImageView star = (ImageView)findViewById(R.id.star);
        star.setTag(0);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int)v.getTag() == 0) {
                    star.setImageResource(R.drawable.full_star);
                    star.setTag(1);
                }
                else {
                    star.setImageResource(R.drawable.empty_star);
                    star.setTag(0);
                }
            }
        });

        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
