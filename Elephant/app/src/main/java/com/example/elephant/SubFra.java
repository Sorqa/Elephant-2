package com.example.elephant;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class SubFra extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subfra_main);


        final CheckBox cb1 = (CheckBox)findViewById(R.id.ai);
        final CheckBox cb2 = (CheckBox)findViewById(R.id.bi);


        Button b = (Button)findViewById(R.id.btn1);
        final TextView tv = (TextView)findViewById(R.id.textView2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if(cb1.isChecked() == true) result += " "+ cb1.getText().toString();
                if(cb2.isChecked() == true) result += " "+ cb2.getText().toString();
                tv.setText("주문목록:" + result);
            }
        });
    }
}


