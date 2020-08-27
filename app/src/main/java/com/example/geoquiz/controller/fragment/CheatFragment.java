package com.example.geoquiz.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquiz.R;

public class CheatFragment extends Fragment {

    public static final String EXTRA_IS_CHEAT = "com.example.geoquiz.isCheat";
    public static final String KEY_PRESSED_CHEAT = "KEY_PRESSED_CHEAT";

    private TextView mTextViewAnswer;
    private Button mButtonShowAnswer;

    private boolean mIsAnswerTrue;
    private boolean pressedCheat = false;

    public CheatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cheat, container, false);

        mIsAnswerTrue = getActivity().getIntent().getBooleanExtra(QuizeDefaultFragment.EXTRA_QUESTION_ANSWER, false);

        findViews(view);

        if (savedInstanceState != null) {
            if(savedInstanceState.getBoolean(KEY_PRESSED_CHEAT))
                if (mIsAnswerTrue)
                    mTextViewAnswer.setText(R.string.button_true);
                else
                    mTextViewAnswer.setText(R.string.button_false);
        }

        setListeners();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_PRESSED_CHEAT,pressedCheat);
    }


    private void findViews(View view) {
        mTextViewAnswer = view.findViewById(R.id.txtview_answer);
        mButtonShowAnswer = view.findViewById(R.id.btn_show_answer);
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

        getActivity().setResult(Activity.RESULT_OK, intent);
    }
}