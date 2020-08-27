package com.example.geoquiz.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.example.geoquiz.controller.LoginActivity;
import com.example.geoquiz.controller.SettingActivity;
import com.example.geoquiz.controller.activity.CheatActivity;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.model.Setting;
import com.example.geoquiz.ripository.QuestionRepository;

import java.util.UUID;

public class QuizeDefaultFragment extends Fragment {

    public static final String TAG = "QuizActivity";
    public static final String BUNDLE_KEY_CURRENT_INDEX = "CurrentIndex";
    public static final String KEY_SERIALIZABLE = "KEY_SERIALIZABLE";
    public static final String KEY_SCORE = "key Score";
    public static final String QUESTIONS_ARRAY = "questions_array";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoquiz.questionAnswer";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_CODE_setting = 1;
    public static final String KEY_SETTING = "keysetting";
    public static final int REQUEST_CODE_LOGIN = 2;
    public static final String EXTRA_IS_ANSWER_THE_QUESTION = "Extra is answer the question";


    private TextView mTextViewQuestion;
    private Button mButtonReset;
    private ImageView mButtonTrue, mButtonFalse, mButtonSetting, mButtonCheat, mAddUser;
    private ImageButton mImageButtonNext, mImageButtonPrev, mImageButtonLast, mImageButtonFirst;
    private TextView mTextViewCount, mTextViewUser;
    private LinearLayout mLinearLayoutMain;
    private LinearLayout mLinearLayoutResult;
    private LinearLayout mLinearLayoutNext;
    private LinearLayout mLinearLayoutPrev;
    private LinearLayout mRootLayoutMain;

    private int counter = 0;
    private int mCurrentIndex = 0;
    private int state = 0;
    private int flag = 0;

    private QuestionRepository mRepository;
    private Question mQuestion;
  //  int textResId;

    public QuizeDefaultFragment() {
        // Required empty public constructor
    }

    private Question[] mQuestionBank;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionBank = new Question[]{
                new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, true),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false)
        };

        mRepository = QuestionRepository.getInstance();

        UUID id = (UUID) getActivity().getIntent().getSerializableExtra(QuizListFragment.EXTRA_QUESTION_ID);
//        textResId =(Integer) getActivity().getIntent().getSerializableExtra(QuizListFragment.EXTRA_QUESTION_RES_ID);
//        boolean isAnswer = (boolean) getActivity().getIntent().getSerializableExtra(QuizListFragment.EXTRA_IS_TRUE_ANSWER);

        QuestionRepository questionRepository = QuestionRepository.getInstance();
        mQuestion = questionRepository.getQuestion(id);
      //  mTextViewQuestion.setText(textResId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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

//            changeSetting = (Setting) savedInstanceState.getSerializable(KEY_SETTING);
//
//            if (changeSetting.isSave()) {
//                checkedSetting(changeSetting);
//                setSettingColor(changeSetting);
//                setQuestionSize(changeSetting);
//                changeSetting.setSave(false);
//            }

        } else {
            Log.d(TAG, "SavedInstanceState is NULL!!!");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quize_default, container, false);

        findViews(view);
        mTextViewCount.setText("" + counter);
        //mTextViewQuestion.setText(textResId);
        setListeners();
        endScreen();
        updateQuestion();
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_CHEAT) {
            boolean mIsCheater = data.getBooleanExtra(CheatFragment.EXTRA_IS_CHEAT, false);
            mQuestionBank[mCurrentIndex].setCheat(mIsCheater);

        } else if (requestCode == REQUEST_CODE_setting) {

            Setting changeSetting = new Setting();
            changeSetting = (Setting) data.getSerializableExtra(SettingActivity.EXTRA_SETTING_OBJECT);

            if (changeSetting.isSave()) {

                checkedSetting(changeSetting);
                setSettingColor(changeSetting);
                setQuestionSize(changeSetting);
                changeSetting.setSave(false);
            }

        } else if(requestCode == REQUEST_CODE_LOGIN){
            String userName = data.getStringExtra(LoginActivity.LOGIN_USERNAME);
            mTextViewUser.setText(userName);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(KEY_SCORE, counter);
        outState.putSerializable(QUESTIONS_ARRAY, mQuestionBank);

      //  outState.putSerializable(KEY_SETTING, changeSetting);
    }

    private void findViews(View view) {
        mTextViewQuestion = view.findViewById(R.id.txtview_question_text);
        mButtonTrue = view.findViewById(R.id.btn_true);
        mButtonFalse = view.findViewById(R.id.btn_false);
        mImageButtonNext = view.findViewById(R.id.img_btn_next);
        mImageButtonPrev = view.findViewById(R.id.img_btn_prev);
        mImageButtonLast = view.findViewById(R.id.img_btn_last);
        mImageButtonFirst = view.findViewById(R.id.img_btn_first);
        mTextViewCount = view.findViewById(R.id.count);
        mButtonReset = view.findViewById(R.id.btn_reset);
        mLinearLayoutMain = view.findViewById(R.id.main);
        mLinearLayoutResult = view.findViewById(R.id.result);
        mButtonCheat = view.findViewById(R.id.btn_cheat);
        mButtonSetting = view.findViewById(R.id.btn_setting);
        mRootLayoutMain = view.findViewById(R.id.root_layout_main);
        mAddUser = view.findViewById(R.id.add_user);
        mTextViewUser = view.findViewById(R.id.txtView_username);
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
                    mButtonCheat.setVisibility(View.VISIBLE);
                }
                mButtonTrue.setEnabled(true);
                mButtonFalse.setEnabled(true);
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank[mCurrentIndex].isAnswerTrue());

                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);

                startActivityForResult(intent, REQUEST_CODE_setting);
            }
        });

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);

                startActivityForResult(intent , REQUEST_CODE_LOGIN);
            }
        });
    }

    private void updateQuestion() {
        // mIsCheater = false;
        if (!mQuestionBank[mCurrentIndex].isMflag())
            disableAnswer(false);
        else
            disableAnswer(true);

        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
        endScreen();
    }

    private void endScreen() {
        if (state == mQuestionBank.length || flag == mQuestionBank.length) {
            mLinearLayoutMain.setVisibility(View.GONE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTextViewQuestion.setVisibility(View.GONE);
                mLinearLayoutNext.setVisibility(View.GONE);
                mLinearLayoutPrev.setVisibility(View.GONE);
                mButtonCheat.setVisibility(View.GONE);
            }

        }
    }

    private void disableAnswer(boolean flag) {
        mButtonFalse.setEnabled(flag);
        mButtonTrue.setEnabled(flag);
    }

    private void checkAnswer(boolean userPressed) {

        if (mQuestionBank[mCurrentIndex].isCheat() == true)
            Toast.makeText(getActivity(), R.string.toast_judgment, Toast.LENGTH_SHORT).show();

        else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
                Toast.makeText(getActivity(), R.string.toast_correct, Toast.LENGTH_LONG)
                        .show();
                mTextViewCount.setText("" + (++counter));
            } else
                Toast.makeText(getActivity(), R.string.toast_incorrect, Toast.LENGTH_LONG)
                        .show();
        }
        mQuestionBank[mCurrentIndex].setMflag(false);
        disableAnswer(false);
        flag++;
        state++;

//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_IS_ANSWER_THE_QUESTION,true);
//        getActivity().setResult(Activity.RESULT_OK , intent);
    }

    private void checkedSetting(Setting setting) {

        if (setting.isButtonTrue()) {
            setHideButtons(mButtonTrue);
        }
        if (setting.isButtonFalse())
            setHideButtons(mButtonFalse);

        if (setting.isButtonCheat())
            setHideButtons(mButtonCheat);

        if (setting.isButtonNext())
            mImageButtonNext.setEnabled(false);

        if (setting.isButtonPrev())
            mImageButtonPrev.setEnabled(false);

        if (setting.isButtonFirst())
            mImageButtonFirst.setEnabled(false);

        if (setting.isButtonLast())
            mImageButtonLast.setEnabled(false);
    }

    private void setHideButtons(ImageView btn) {
        for (int i = 0; i < mQuestionBank.length; i++) {
            if (mQuestionBank[i].isMflag()) {
                btn.setEnabled(false);
                mQuestionBank[i].setMflag(false);
            }
        }
    }

    private void setSettingColor(Setting setting) {
        if (setting.getColor() != null)
            mRootLayoutMain.setBackgroundColor(Color.parseColor(setting.getColor()));
    }

    private void setQuestionSize(Setting setting) {
        int size = setting.getQuestionSize();
        if (size != 0)
            mTextViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + mCurrentIndex);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + mCurrentIndex);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + mCurrentIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + mCurrentIndex);
    }
}