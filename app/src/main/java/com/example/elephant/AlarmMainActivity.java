package com.example.elephant;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

public class AlarmMainActivity extends AppCompatActivity {
    //private int REQUEST_TEST = 200;
    PreferenceManager pref;
    RecyclerView recyclerView;
    CustomListsAdapter listsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_main);
        pref = new PreferenceManager();

        Button plus = (Button)findViewById(R.id.plus);
        plus.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingMain.class);
            activityResultLauncher.launch(intent);                                            //나중에 결과를 받을 때 이 요청이 어떤 요청인지를 구분
        });


        //리사이클러뷰 세팅
        LinearLayoutManager linearLayoutManager;
        recyclerView = findViewById(R.id.listView);//리사이클러뷰 findView
        linearLayoutManager = new LinearLayoutManager(AlarmMainActivity.this, LinearLayoutManager.VERTICAL, false);

        listsAdapter = new CustomListsAdapter(AlarmMainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        recyclerView.setAdapter(listsAdapter);//adapter 세팅


        //쉐어드 모든 키 벨류 가져오기
        SharedPreferences prefb =getSharedPreferences("memo_contain", MODE_PRIVATE);
        Collection<?> col_val =  prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();

        while(it_val.hasNext() && it_key.hasNext()) {
            String key = (String) it_key.next();
            String value = (String) it_val.next();
            try {
                JSONObject jsonObject = new JSONObject(value);
                if (jsonObject.has("time")) { // "time" 키가 있는지 확인
                    String time = jsonObject.getString("time");
                    String content = jsonObject.getString("contents");
                    String day = jsonObject.getString("day");
                    listsAdapter.addItem(new List(content, time, day, key));
                } else {
                    Log.d("SubFra", "No value for time for key: " + key); // "time" 키가 없는 경우 로그를 남김
                }
            } catch (JSONException e) {
                Log.d("SubFra", "JSONObject: " + e);
            }
        }
        listsAdapter.notifyDataSetChanged();

        //pref.clear(MainActivity.this);
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        String get_date = intent.getStringExtra("day");
                        String get_time = intent.getStringExtra("time");
                        String get_content = intent.getStringExtra("contents");
                        String get_now = intent.getStringExtra("now");

                        listsAdapter.addItem(new List(get_content, get_time, get_date, get_now));
                        listsAdapter.notifyDataSetChanged();
                        Toast.makeText(AlarmMainActivity.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AlarmMainActivity.this, "저장 되지 않음", Toast.LENGTH_SHORT).show();
                }
            });



}









