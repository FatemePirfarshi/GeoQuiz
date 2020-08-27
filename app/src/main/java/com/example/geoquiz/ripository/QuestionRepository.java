package com.example.geoquiz.ripository;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionRepository {

    private static final int QUESTION_SIZE = 30;
    private static QuestionRepository sInstance;

    public static QuestionRepository getInstance() {
        if (sInstance == null)
            sInstance = new QuestionRepository();
        return sInstance;
    }

    private List<Question> mQuestions;

    private QuestionRepository() {
        mQuestions = new ArrayList<>();

        mQuestions.add(new Question(R.string.question_australia, false));
        mQuestions.add(new Question(R.string.question_oceans, true));
        mQuestions.add(new Question(R.string.question_mideast, false));
        mQuestions.add(new Question(R.string.question_africa, true));
        mQuestions.add(new Question(R.string.question_americas, false));
        mQuestions.add(new Question(R.string.question_asia, false));
        mQuestions.add(new Question(R.string.question_australia, false));
        mQuestions.add(new Question(R.string.question_oceans, true));
        mQuestions.add(new Question(R.string.question_mideast, false));
        mQuestions.add(new Question(R.string.question_africa, true));
        mQuestions.add(new Question(R.string.question_americas, false));
        mQuestions.add(new Question(R.string.question_asia, false));
        mQuestions.add(new Question(R.string.question_australia, false));
        mQuestions.add(new Question(R.string.question_oceans, true));
        mQuestions.add(new Question(R.string.question_mideast, false));
        mQuestions.add(new Question(R.string.question_africa, true));
        mQuestions.add(new Question(R.string.question_americas, false));
        mQuestions.add(new Question(R.string.question_asia, false));
        mQuestions.add(new Question(R.string.question_australia, false));
        mQuestions.add(new Question(R.string.question_oceans, true));
        mQuestions.add(new Question(R.string.question_mideast, false));
        mQuestions.add(new Question(R.string.question_africa, true));
        mQuestions.add(new Question(R.string.question_americas, false));
        mQuestions.add(new Question(R.string.question_asia, false));
        mQuestions.add(new Question(R.string.question_australia, false));
        mQuestions.add(new Question(R.string.question_oceans, true));
        mQuestions.add(new Question(R.string.question_mideast, false));
        mQuestions.add(new Question(R.string.question_africa, true));
        mQuestions.add(new Question(R.string.question_americas, false));
        mQuestions.add(new Question(R.string.question_asia, false));

    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
    }

    public Question getQuestion(UUID id) {
        for (Question question : mQuestions) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
    }

}
