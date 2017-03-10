package com.example.owner.lab3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Map<String, Object>> data = new ArrayList<>();
        final String[] alphaList = new String[] {"A", "E", "D", "E", "F", "J", "I", "M", "J", "P"};
        final String[] nameList = new String[] {"Aaron", "Elvis", "David", "Edwin", "Frank", "Joshua", "Ivan", "Mark", "Joseph", "Phoebe"};
        final String [] phoneNumList = new String[] {"17715523654", "18825653224", "15052116654", "18854211875", "13955188541", "13621574410", "15684122771", "17765213579", "13315466578", "17895466428"};
        final String [] typeList = new String[] {"手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机", "手机"};
        final String [] locationList = new String[] {"江苏苏州电信", "广东揭阳移动", "江苏无锡移动", "山东青岛移动", "安徽合肥移动", "江苏苏州移动", "山东烟台联通", "广东珠海电信", "河北石家庄电信", "山东东营移动"};
        final String [] colorList = new String[] {"#BB4C3B", "#c48d30", "#4469b0", "#20A17B", "#BB4C3B", "#c48d30", "#4469b0", "#20A17B", "#BB4C3B", "#c48d30"};

        for (int i = 0; i < 10; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("alpha", alphaList[i]);
            temp.put("name", nameList[i]);
            temp.put("phoneNum", phoneNumList[i]);
            temp.put("type", typeList[i]);
            temp.put("location", locationList[i]);
            temp.put("color", colorList[i]);
            data.add(temp);
        }

        ListView contactsList = (ListView) findViewById(R.id.contactsList);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item,
                new String[] {"alpha", "name"}, new int[] {R.id.alpha, R.id.name});
        contactsList.setAdapter(simpleAdapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("name", data.get(position).get("name").toString());
                bundle.putString("phoneNum", data.get(position).get("phoneNum").toString());
                bundle.putString("type", data.get(position).get("type").toString());
                bundle.putString("location", data.get(position).get("location").toString());
                bundle.putString("color", data.get(position).get("color").toString());

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        contactsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                        setTitle("删除联系人").
                        setMessage("确定删除联系人" + data.get(position).get("name").toString() + "?").
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        }).
                        create();
                alertDialog.show();
                return true;
            }
        });
    }
}
