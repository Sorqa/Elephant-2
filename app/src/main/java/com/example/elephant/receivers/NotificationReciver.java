package com.example.elephant.receivers;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.elephant.R;
import com.example.elephant.AlarmMainActivity;

public class NotificationReciver extends BroadcastReceiver {

    private String TAG = this.getClass().getSimpleName();

    NotificationManager manager;
    NotificationCompat.Builder builder;


    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(TAG, "onReceive 알람이 들어옴!!");
        String contentValue = intent.getStringExtra("content");
        Log.e(TAG, "onReceive contentValue값 확인 : " + contentValue);

        builder = null;

        //푸시 알림을 보내기위해 시스템에 권한을 요청하여 생성
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }




        //알림창 클릭 시 리스트 화면 으로 이동 체크하기 위해
        Intent intent2 = new Intent(context, AlarmMainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,101,intent2,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE );
        //알림 생성
        builder.setContentTitle(intent.getStringExtra(contentValue)) //알람 내용
                .setContentText(intent.getStringExtra("content"))
                .setSmallIcon(R.drawable.blackdrugicon)                        //알림창 아이콘
                .setAutoCancel(true)                                       //알림창 터치시 자동 삭제
                .setContentIntent(pendingIntent);


        Notification notification = builder.build();                        //푸시알림 빌드

        //NotificationManager를 이용하여 푸시 알림 보내기
        manager.notify(1,notification);
        Log.e(TAG,"알람 왔음!!");


    }
}




