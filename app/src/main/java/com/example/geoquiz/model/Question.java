package com.example.geoquiz.model;

public class Question {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;
    private boolean mflag = true;

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
