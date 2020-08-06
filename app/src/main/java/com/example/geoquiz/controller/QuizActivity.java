package com.example.geoquiz.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity";
    public static final String BUNDLE_KEY_CURRENT_INDEX = "CurrentIndex";
    public static final String KEY_SERIALIZABLE = "KEY_SERIALIZABLE";
    public static final String KEY_SCORE = "key Score";
    public static final String QUESTIONS_ARRAY = "questions_array";
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
    private LinearLayout mLinearLayoutNext;
    private LinearLayout mLinearLayoutPrev;

    private int counter = 0;
    private int mCurrentIndex = 0;
    private int state = 0;
    private int flag = 0;

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

        Log.d(TAG, "onCreate: " + mCurrentIndex);

        if (savedInstanceState != null) {
            Log.d(TAG, "SavedInstanceState: " + savedInstanceState);

            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            counter = savedInstanceState.getInt(KEY_SCORE, 0);

            mQuestionBank = (Question[]) savedInstanceState.getSerializable(QUESTIONS_ARRAY);

            for (int i = 0; i < mQuestionBank.length; i++) {
                if (!mQuestionBank[i].isMflag())
                    flag++;
            }

        } else {
            Log.d(TAG, "SavedInstanceState is NULL!!!");
        }

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);

        //if we want to change logic we must first find the view objects (it must have "id")
        findViews();
        mTextViewCount.setText("" + counter);
        setListeners();
        endScreen();
        updateQuestion();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + mCurrentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + mCurrentIndex);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + mCurrentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + mCurrentIndex);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(KEY_SCORE, counter);
        outState.putSerializable(QUESTIONS_ARRAY, mQuestionBank);

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
        mLinearLayoutNext = findViewById(R.id.next_linear);
        mLinearLayoutPrev = findViewById(R.id.prev_linear);
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
                state = 0;
                mTextViewCount.setText("" + counter);
                for (int i = 0; i < mQuestionBank.length; i++) {
                    mQuestionBank[i].setMflag(true);
                }
                mLinearLayoutMain.setVisibility(View.VISIBLE);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mTextViewQuestion.setVisibility(View.VISIBLE);
                    mLinearLayoutNext.setVisibility(View.VISIBLE);
                    mLinearLayoutPrev.setVisibility(View.VISIBLE);
                }
                mButtonTrue.setEnabled(true);
                mButtonFalse.setEnabled(true);
            }
        });
    }

    private void updateQuestion() {

        if (!mQuestionBank[mCurrentIndex].isMflag())
            disableAnswer(false);
        else
            disableAnswer(true);

        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
        endScreen();
    }

    private void endScreen() {
        if (state == mQuestionBank.length || flag == mQuestionBank.length ) {
            mLinearLayoutMain.setVisibility(View.GONE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTextViewQuestion.setVisibility(View.GONE);
                mLinearLayoutNext.setVisibility(View.GONE);
                mLinearLayoutPrev.setVisibility(View.GONE);
            }

        }
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
            Toast.makeText(QuizActivity.this, R.string.toast_incorrect, Toast.LENGTH_LONG)
                    .show();
        }
        mQuestionBank[mCurrentIndex].setMflag(false);
        disableAnswer(false);
        flag++;
        state++;
    }

}