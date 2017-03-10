package com.example.owner.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StaticActivity extends AppCompatActivity {
    public static final String STATICACTION = "com.example.owner.lab5.staticreceiver";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        final List<Map<String, Object>> data = new ArrayList<>();
        final int[] imageList = new int[] {R.mipmap.ic_apple, R.mipmap.ic_banana, R.mipmap.ic_cherry, R.mipmap.ic_coco,
                R.mipmap.ic_kiwi, R.mipmap.ic_orange, R.mipmap.ic_pear, R.mipmap.ic_strawberry, R.mipmap.ic_watermelon};
        final String[] nameList = new String[] {"Apple", "Banana", "Cherry", "Coco", "Kiwi", "Orange", "Pear", "Strawberry", "Watermelon"};

        for (int i = 0; i < 9; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("image", imageList[i]);
            temp.put("name", nameList[i]);
            data.add(temp);
        }

        ListView fruitList = (ListView) findViewById(R.id.fruitList);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item,
                new String[] {"image", "name"}, new int[] {R.id.image, R.id.name});
        fruitList.setAdapter(simpleAdapter);

        fruitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Map<String, Object> temp = (Map<String, Object>) parent.getAdapter().getItem(position);
                bundle.putString("title", "静态广播");
                bundle.putString("content", (String) temp.get("name"));
                bundle.putInt("image", (int) temp.get("image"));
                Intent intent = new Intent(STATICACTION);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
    }
}
