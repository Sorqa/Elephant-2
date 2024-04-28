package com.example.elephant;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView cancelButton, okButton;
    private int repeatInterval;
    private OnRepeatIntervalSelectedListener listener;
    private RadioGroup radioGroup;

    public interface OnRepeatIntervalSelectedListener {
        void onRepeatIntervalSelected(int interval);
    }


    public CustomDialog(Context context, OnRepeatIntervalSelectedListener listener) {
        super(context);
        this.listener = listener;
    }
    //추가된 메서드: 리스너 설정
    public void setOnRepeatIntervalSelectedListener(OnRepeatIntervalSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        radioGroup = findViewById(R.id.radioGroup);

        cancelButton = findViewById(R.id.tv_cancel);
        okButton = findViewById(R.id.ok);

        // Set click listeners
        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        // Set default repeat interval
        repeatInterval = 0; // You can set your default interval here
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.ok:
                // Determine the selected repeat interval
                int checkedId = radioGroup.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.test:
                        repeatInterval = 2;
                        break;
                    case R.id.three:
                        repeatInterval = 3;
                        break;
                    case R.id.six:
                        repeatInterval = 6;
                        break;
                    case R.id.half:
                        repeatInterval = 12;
                        break;
                    case R.id.day:
                        repeatInterval = 24;
                        break;
                    case R.id.free:
                        repeatInterval = 0; // 직접 선택 시
                        break;

                }


                // 리스너 선택 적용
                listener.onRepeatIntervalSelected(repeatInterval);
                dismiss();
                break;
            default:
                break;
        }
    }
}

