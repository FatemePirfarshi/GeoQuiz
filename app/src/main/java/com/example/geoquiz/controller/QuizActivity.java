package com.example.geoquiz.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    private TextView mTextViewQuestion;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrev;
    private ImageButton mImageButtonLast;
    private ImageButton mImageButtonFirst;
    private TextView mTextViewCount;
    private Button mButtonReset;
    private LinearLayout mLinearLayoutMain;
    private LinearLayout mLinearLayoutResult;

    private int counter = 0;
    private int mCurrentIndex = 0;
    private int state = 1;

    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };

    /**
     * This method is used to crete ui for activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);

        //if we want to change logic we must first find the view objects (it must have "id")
        findViews();
        mTextViewCount.setText("" + counter);
        setListeners();
        updateQuestion();
    }

    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txtview_question_text);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mImageButtonNext = findViewById(R.id.img_btn_next);
        mImageButtonPrev = findViewById(R.id.img_btn_prev);
        mImageButtonLast = findViewById(R.id.img_btn_last);
        mImageButtonFirst = findViewById(R.id.img_btn_first);
        mTextViewCount = findViewById(R.id.count);
        mButtonReset = findViewById(R.id.btn_reset);
        mLinearLayoutMain = findViewById(R.id.main);
        mLinearLayoutResult = findViewById(R.id.result);
    }

    private void setListeners() {

        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
            }
        });

        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 0;
                mTextViewCount.setText("" + counter);
                for (int i = 0; i < mQuestionBank.length; i++) {
                    mQuestionBank[i].setMflag(true);
                }
                mLinearLayoutMain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateQuestion() {
        if(state == mQuestionBank.length){
            mLinearLayoutMain.setVisibility(View.GONE);
        }

        if (!mQuestionBank[mCurrentIndex].isMflag())
            disableAnswer(false);
        else
            disableAnswer(true);
        state++;
        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
    }

    private void disableAnswer(boolean flag) {
        mButtonFalse.setEnabled(flag);
        mButtonTrue.setEnabled(flag);
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
            Toast.makeText(QuizActivity.this, R.string.toast_correct, Toast.LENGTH_LONG)
                    .show();
            mTextViewCount.setText("" + (++counter));
        } else {
            Toast.makeText(QuizActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT)
                    .show();
        }
        mQuestionBank[mCurrentIndex].setMflag(false);
        disableAnswer(false);
    }
}