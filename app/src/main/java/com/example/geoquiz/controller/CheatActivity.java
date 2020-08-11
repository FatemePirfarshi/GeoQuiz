package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquiz.R;

import java.time.Instant;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "com.example.geoquiz.isCheat";
    public static final String KEY_PRESSED_CHEAT = "KEY_PRESSED_CHEAT";
    private TextView mTextViewAnswer;
    private Button mButtonShowAnswer;

    private boolean mIsAnswerTrue;
    private boolean pressedCheat = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mIsAnswerTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_QUESTION_ANSWER, false);


        findViews();

        if (savedInstanceState != null) {
            if(savedInstanceState.getBoolean(KEY_PRESSED_CHEAT))
                if (mIsAnswerTrue)
                    mTextViewAnswer.setText(R.string.button_true);
                else
                    mTextViewAnswer.setText(R.string.button_false);
        }
        setListeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_PRESSED_CHEAT,pressedCheat);
    }

    private void findViews() {
        mTextViewAnswer = findViewById(R.id.txtview_answer);
        mButtonShowAnswer = findViewById(R.id.btn_show_answer);
    }

    private void setListeners() {
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswerTrue)
                    mTextViewAnswer.setText(R.string.button_true);
                else
                    mTextViewAnswer.setText(R.string.button_false);

                setShowAnswerResult(true);
                pressedCheat = true;
            }
        });
    }

    private void setShowAnswerResult(boolean isCheet) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheet);

        setResult(RESULT_OK, intent);
    }
}