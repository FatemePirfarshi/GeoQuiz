package com.example.geoquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Setting;

import java.util.Random;

public class SettingActivity extends AppCompatActivity {

    public static final String EXTRA_SETTING_OBJECT = "com.example.geoquiz.settingObject";

    private RadioButton mRadioButtonTrue, mRadioButtonFalse, mRadioButtonNext, mRadioButtonPrev,
            mRadioButtonFirst, mRadioButtonLast, mRadioButtonCheat;

    private RadioGroup mRadioGroupSize;
    private RadioGroup mRadioGroupColor;
    private Button mButtonSave;
    private Button mButtonDiscard;
    private LinearLayout mRootLayoutSetting;

    private Setting mSetting = new Setting();

    private int[] mButtonsHide = {R.id.radio_btn_true, R.id.radio_btn_false, R.id.radio_btn_next,
            R.id.radio_btn_prev, R.id.radio_btn_first, R.id.radio_btn_last, R.id.radio_btn_cheat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViews();
        setListeners();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SETTING_OBJECT, mSetting);
        setResult(RESULT_OK , intent);
    }

    private void findViews() {
        mRadioGroupSize = findViewById(R.id.radio_gp_size);
        mRadioGroupColor = findViewById(R.id.radio_gp_color);
        mButtonSave = findViewById(R.id.btn_save);
        mButtonDiscard = findViewById(R.id.btn_discard);
        mRootLayoutSetting = findViewById(R.id.root_layout_setting);
    }

    private void setListeners() {

        for (final int id : mButtonsHide) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setButtonsSetting(id);
                }
            });
        }

        mRadioGroupColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setColor(checkedId);
            }
        });

        mRadioGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSize(checkedId);
            }
        });
    }

    private void setSize(int id) {
        if(id == R.id.radio_btn_small)
            mSetting.setQuestionSize(14);
        if(id == R.id.radio_btn_medium)
            mSetting.setQuestionSize(18);
        if(id == R.id.radio_btn_large)
            mSetting.setQuestionSize(26);
    }

    private void setColor(int id) {
        if (id == R.id.radio_btn_red)
            setColor("#FFAFAF");
        else if (id == R.id.radio_btn_blue)
            setColor("#7FEDED");
        else if (id == R.id.radio_btn_green)
            setColor("#B2FDB5");
        else
            setColor("#ffffff");
    }

    private void setColor(String color) {
        mRootLayoutSetting.setBackgroundColor(Color.parseColor(color));
    }

    private void setButtonsSetting(int id) {
        switch (id) {
            case R.id.radio_btn_true:
                mSetting.setButtonTrue(true);
                break;
            case R.id.radio_btn_false:
                mSetting.setButtonFalse(true);
                break;
            case R.id.radio_btn_next:
                mSetting.setButtonNext(true);
                break;
            case R.id.radio_btn_prev:
                mSetting.setButtonPrev(true);
                break;
            case R.id.radio_btn_first:
                mSetting.setButtonFirst(true);
                break;
            case R.id.radio_btn_last:
                mSetting.setButtonLast(true);
                break;
            case R.id.radio_btn_cheat:
                mSetting.setButtonCheat(true);
                break;
        }
    }
}