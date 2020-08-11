package com.example.geoquiz.model;

import java.io.Serializable;

public class Question implements Serializable {

    private static final long SeialVersionUID = 1L;
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;
    private boolean mflag = true;
    private boolean cheat = false;

    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public int getQuestionTextResId() {
        return mQuestionTextResId;
    }

    public void setQuestionTextResId(int questionTextResId) {
        mQuestionTextResId = questionTextResId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }

    public Question(int questionTextResId, boolean isAnswerTrue) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
    }

    public boolean isMflag() {
        return mflag;
    }

    public void setMflag(boolean mflag) {

        this.mflag = mflag;
    }

    public Question() {
    }
}
