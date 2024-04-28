package com.example.elephant;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

public class SubBread extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subbread_main);

        CheckBox checkBox = (CheckBox) findViewById(R.id.au);
        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.bu);
        checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.cu);
        checkBox3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.du);
        checkBox4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.eu);
        checkBox5.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox6 = (CheckBox) findViewById(R.id.fu);
        checkBox6.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox7 = (CheckBox) findViewById(R.id.gu);
        checkBox7.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;
        CheckBox checkBox8 = (CheckBox) findViewById(R.id.hu);
        checkBox8.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
            }
        }) ;

    }
}
