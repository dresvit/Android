package com.example.owner.lab9;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String NAMESPACE = "http://WebXml.com.cn/";
    private static final String URL = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
    private static final String OPERATION_NAME = "getWeather";
    private static final String SOAP_ACTION = "http://WebXml.com.cn/getWeather";
    private static final int UPDATE_CONTENT = 0;
    private static final int NOT_EXIST = 1;
    private String[] titleList = new String[] {"紫外线指数", "感冒指数", "穿衣指数", "洗车指数", "运动指数", "空气污染指数"};
    private String[] weatherInfo = new String[30];
    private ArrayList<Weather> forecast;
    private View showView;
    private TextView cityName;
    private TextView curTime;
    private TextView curTem;
    private TextView allTem;
    private TextView humidity;
    private TextView quality;
    private TextView wind;
    private ListView infoList;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_CONTENT:
                    cityName.setText(weatherInfo[0]);
                    curTime.setText(weatherInfo[1]+" 更新");
                    curTem.setText(weatherInfo[2]);
                    allTem.setText(weatherInfo[3]);
                    humidity.setText(weatherInfo[4]);
                    quality.setText(weatherInfo[5]);
                    wind.setText(weatherInfo[6]);

                    List<Map<String, Object>> data = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        Map<String, Object> temp = new LinkedHashMap<>();
                        temp.put("title", titleList[i]);
                        temp.put("info", weatherInfo[i+7]);
                        data.add(temp);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.item,
                            new String[] {"title", "info"}, new int[] {R.id.title, R.id.info});
                    infoList.setAdapter(simpleAdapter);

                    adapter = new WeatherAdapter(MainActivity.this, forecast);
                    recyclerView.setAdapter(adapter);

                    showView.setVisibility(View.VISIBLE);
                    break;
                case NOT_EXIST:
                    showView.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "当前城市不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        cityName = (TextView) findViewById(R.id.cityName);
        curTime = (TextView) findViewById(R.id.curTime);
        curTem = (TextView) findViewById(R.id.curTem);
        allTem = (TextView) findViewById(R.id.allTem);
        humidity = (TextView) findViewById(R.id.humidity);
        quality = (TextView) findViewById(R.id.quality);
        wind = (TextView) findViewById(R.id.wind);
        infoList = (ListView) findViewById(R.id.infoList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        showView = findViewById(R.id.showView);

        showView.setVisibility(View.INVISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectionAvailable()) {
                    sendRequest();
                } else {
                    showView.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "当前没有可用网络！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText editText = (EditText) findViewById(R.id.editText);
                String city = editText.getText().toString();
                SoapObject request = new SoapObject(NAMESPACE, OPERATION_NAME);
                request.addProperty("theCityCode", city);
                request.addProperty("theUserID", "bd67edc90a664720939a5819fb9b2be1");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                try {
                    transport.call(SOAP_ACTION, envelope);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                SoapObject detail = (SoapObject) bodyIn.getProperty("getWeatherResult");
                String now;

                if (detail.getPropertyAsString(0).equals("查询结果为空")) {
                    handler.sendEmptyMessage(NOT_EXIST);
                } else {
                    weatherInfo[0] = detail.getPropertyAsString(1);
                    weatherInfo[3] = detail.getPropertyAsString(8);

                    now = detail.getPropertyAsString(3);
                    weatherInfo[1] = now.substring(now.indexOf(' ') + 1);

                    now = detail.getPropertyAsString(4);
                    if (now.indexOf("暂无实况") != -1) {
                        weatherInfo[2] = "暂无实况";
                        weatherInfo[4] = "";
                        weatherInfo[6] = "";
                    } else {
                        weatherInfo[2] = now.substring(now.indexOf("气温：") + 3, now.indexOf("℃") + 1);
                        weatherInfo[4] = now.substring(now.indexOf("湿度："));
                        weatherInfo[6] = now.substring(now.indexOf("风力：") + 3, now.indexOf("；湿度"));
                    }

                    now = detail.getPropertyAsString(5);
                    if (now.indexOf("暂无预报") != -1) {
                        weatherInfo[5] = "";
                    } else {
                        int id = now.indexOf("空气质量：");
                        weatherInfo[5] = now.substring(id, now.indexOf("。", id));
                    }

                    now = detail.getPropertyAsString(6);
                    if (now.indexOf("暂无预报") != -1) {
                        weatherInfo[7] = "暂无预报";
                        weatherInfo[8] = "暂无预报";
                        weatherInfo[9] = "暂无预报";
                        weatherInfo[10] = "暂无预报";
                        weatherInfo[11] = "暂无预报";
                        weatherInfo[12] = "暂无预报";
                    } else {
                        int id;
                        id = now.indexOf("紫外线指数：");
                        weatherInfo[7] = now.substring(id + 6, now.indexOf("。", id));
                        id = now.indexOf("感冒指数：");
                        weatherInfo[8] = now.substring(id + 5, now.indexOf("。", id));
                        id = now.indexOf("穿衣指数：");
                        weatherInfo[9] = now.substring(id + 5, now.indexOf("。", id));
                        id = now.indexOf("洗车指数：");
                        weatherInfo[10] = now.substring(id + 5, now.indexOf("。", id));
                        id = now.indexOf("运动指数：");
                        weatherInfo[11] = now.substring(id + 5, now.indexOf("。", id));
                        id = now.indexOf("空气污染指数：");
                        weatherInfo[12] = now.substring(id + 7, now.indexOf("。", id));
                    }

                    forecast = new ArrayList<Weather>();
                    for (int i = 0; i < 7; i++) {
                        int day = i * 5 + 7;
                        now = detail.getPropertyAsString(day);
                        Weather curWeather = new Weather();
                        curWeather.setDate(now.substring(0, now.indexOf(' ')));
                        curWeather.setWeather_description(now.substring(now.indexOf(' ') + 1));
                        curWeather.setTemperature(detail.getPropertyAsString(day + 1));
                        forecast.add(curWeather);
                    }

                    handler.sendEmptyMessage(UPDATE_CONTENT);
                }
            }
        }).start();
    }

    private boolean connectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) return false;
        return true;
    }
}
