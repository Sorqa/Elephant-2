package com.example.elephant;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elephant.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.elephant.receivers.NotificationReciver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class SettingMain extends AppCompatActivity  {
    private ActivityMainBinding binding;
    private Toolbar task_toolbar;
    private EditText task_title;
    private TextView task_date, task_time, task_repetition;



    public PreferenceManager pref;
    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;
    private Calendar calendar;

    int nHour,nMinute;
    private int alarmHour,alarmMinute;

    long repeatIntervalMillis;
    String getTimes;
    String taskTitleText;
    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_alram);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        pref = new PreferenceManager();

        task_toolbar = findViewById(R.id.task_toolbar);
        setupOptionsMenu();

        task_title = findViewById(R.id.task_title);

        task_date = findViewById(R.id.task_date);
        // 현재 날짜로 표현
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dayNow = new SimpleDateFormat("MM'월'dd'일'");
        String ndate = dayNow.format(date);
        task_date.setText(ndate);
        setDay();

        task_time = findViewById(R.id.task_time);
        //현재 시간 표현
        SimpleDateFormat timeNow = new SimpleDateFormat("HH:mm");
        String ntime = timeNow.format(date);
        task_time.setText(ntime);
        // ntime에서 시간과 분 추출
        String[] timeParts = ntime.split(":");
        nHour = Integer.parseInt(timeParts[0]);
        nMinute = Integer.parseInt(timeParts[1]);

        setTime();
        task_repetition = findViewById(R.id.task_repetition);
        setRepet();


    }

    //toolbar 기능
    private void setupOptionsMenu() {
        task_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.save:
                        taskTitleText = task_title.getText().toString();
                        // task_title.getText().toString()의 값이 null 또는 빈 문자열인 경우를 처리
                        if (taskTitleText == null || taskTitleText.isEmpty()) {
                            // 처리할 코드 작성, 예를 들어 경고 메시지 출력
                            Toast.makeText(getApplicationContext(), "알람 내용을 작성해주세요", Toast.LENGTH_SHORT).show();

                        } else {
                            setAlarm(alarmHour + ":" + alarmMinute + ":" + "00");
                        }
                        //prefenece에 값을 저장
                        prebinding();
                        return true;

                    case R.id.delete:
                        // 저장된 알람 데이터 삭제 및 알람 설정 해제
                        deleteAlarmDataAndCancelAlarm();
                        Toast.makeText(getApplicationContext(), "삭제", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


    private void setDay() {
        task_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //실패할 경우
                if (task_date == null) {
                    Log.e("Error", "task_date is null");
                    return;
                }

                // Get current date to set as default in DatePickerDialog
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog and set listener for date selection
                DatePickerDialog datePickerDialog = new DatePickerDialog(SettingMain.this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Update TextView with selected date
                    selectedDate = selectedYear + "년" + (selectedMonth + 1) + "월" + selectedDayOfMonth + "일";
                    task_date.setText(selectedDate);
                }, year, month, dayOfMonth);

                // Show the dialog
                datePickerDialog.show();

            }
        });
    }

    private void setTime() {                                                                        //시간 설정
        task_time.setOnClickListener(view -> {
            TimePickerDialog.OnTimeSetListener timeCallbackMethod = (timePicker, hourOfDay, minute) -> {
                Log.e(TAG, "onTimeSet: 시간 " + hourOfDay + ", 분 " + minute );

                // 변경된 시간으로 textview 업데이트
                task_time.setText(hourOfDay + ":" + minute);

                // 설정된 시간을 필드에 저장
                alarmHour = hourOfDay;
                alarmMinute = minute;
            };

            //스피너모드 타임피커
            TimePickerDialog dialog = new TimePickerDialog(
                    SettingMain.this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    timeCallbackMethod, nHour, nMinute,true);

            //다이알로그 타이틀 설정
            dialog.setTitle("알람 시간 설정");
            //기존테마의 배경을 없앤다
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();


        });
    }



    private void setRepet(){                                                                        //반복 알림 누를 때
        task_repetition.setOnClickListener(view -> {
            CustomDialog dialog = new CustomDialog(SettingMain.this,this::handleRepeatInterval);
            dialog.setOnRepeatIntervalSelectedListener(new CustomDialog.OnRepeatIntervalSelectedListener() {
                @Override
                public void onRepeatIntervalSelected(int interval) {
                    handleRepeatInterval(interval);
                }
            });
            dialog.show();

        });


    }

    private void handleRepeatInterval(int interval) {                                               //반복 설정

        String message =   interval + " 시간 반복";
        switch (interval) {
            case 2:
                repeatIntervalMillis = 2 * 60 * 1000; // 2분 간격

                break;
            case 3:
                repeatIntervalMillis = 3 * 60 * 60 * 1000; // 3시간 간격
                break;
            case 6:
                repeatIntervalMillis = 6 * 60 * 60 * 1000; // 6시간 간격
                break;
            case 12:
                repeatIntervalMillis = 12 * 60 * 60 * 1000; // 12시간 간격
                break;
            case 24:
                repeatIntervalMillis = 24 * 60 * 60 * 1000; // 24시간 간격
                break;
            default:
                repeatIntervalMillis = 0; // 기본값: 반복 없음
                break;
        }
        task_repetition.setText(message);

        Toast.makeText(this, "반복 알람 설정 ", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(String alarmTimeValue) {                                                  //날짜와 함께 시간 설정

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
        calendar.set(Calendar.MINUTE,alarmMinute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


        Intent reci = new Intent(this, NotificationReciver.class);
        reci.putExtra("content", task_title.getText().toString());                            //인텐트내의 정보 저장 주머니
        pendingIntent = PendingIntent.getBroadcast(this,123,reci,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Intent로 값을 AlarmMainActivity에 전달
        Intent intent = new Intent(this,AlarmMainActivity.class);
        intent.putExtra("day", selectedDate);
        long now = System.currentTimeMillis();
        Date sDate = new Date(now);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = formatter.format(sDate);



        intent.putExtra("now",getTime);

        SimpleDateFormat simpleDate = new SimpleDateFormat("MM'월'dd'일' HH:mm:ss '예약'");
        getTimes = simpleDate.format(calendar.getTimeInMillis());


        intent.putExtra("time", getTimes);

        intent.putExtra("contents",task_title.getText().toString());
        Log.e(TAG,"AlarmMain 데이터 연결");
        setResult(RESULT_OK, intent);


        if (alarmManager != null) {
            // 반복 알람 설정
            if (repeatIntervalMillis > 0) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatIntervalMillis, pendingIntent);
            } else {
                // 단일 알람 설정
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            Toast.makeText(this, "알람 설정 성공", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "알람 설정 실패", Toast.LENGTH_SHORT).show();
        }

    }

    //preferenece 저장
    private void prebinding(){
        String save_form = "{\"contents\":\""+taskTitleText+"\",\"time\":\""+getTimes+"\",\"day\":\""+selectedDate+"\"}";            //day 대신 반복으로 변경해야함
        // key값이 겹치지 않도록 현재 시간으로 부여
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = simpleDate.format(mDate);
        // 파일 내용 생성
        Log.d("SettingMain","제목 : "+taskTitleText+",  현재시간 : "+getTime);
        //PreferenceManager 클래스에서 저장에 관한 메소드를 관리
        pref.setString(getApplication(),getTime,save_form);

        Toast.makeText(getApplicationContext(), "저장", Toast.LENGTH_SHORT).show();

    }

    // 저장된 알람 데이터 삭제 하지만 test를 위해 전체 삭제를 했으니 추후 변경
    private boolean deleteAlarmData() {
        pref.clear(getApplicationContext());
        return false;
    }
    // 저장된 알람 데이터 삭제 및 알람 설정 해제
    private void deleteAlarmDataAndCancelAlarm() {
        // 파일 삭제
        deleteAlarmData();

        // 알람 설정 해제
        Intent alarmIntent = new Intent(this, NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(this, "알람 설정 해제", Toast.LENGTH_SHORT).show();
    }



}




