package com.example.geoquiz.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.geoquiz.R;
import com.example.geoquiz.controller.activity.QuizActivity;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.ripository.QuestionRepository;

import java.util.List;

public class QuizListFragment extends Fragment {

    public static final int REQUEST_CODE_QUIZ_ACTIVITY = 0;
    public static final String EXTRA_QUESTION_ID = "extra question res id";
    public static final String EXTRA_QUESTION_RES_ID = "question res id";
    public static final String EXTRA_IS_TRUE_ANSWER = "extra is true answer";
    private RecyclerView mRecyclerView;

    private QuestionRepository mQuestionRepository;
    private Question mQuestion;

    public QuizListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview_question_list);
    }

    private void initViews() {
        View view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        QuestionRepository questionRepository = QuestionRepository.getInstance();
        List<Question> questions = questionRepository.getQuestions();
        QuestionAdapter questionAdapter = new QuestionAdapter(questions);

        mRecyclerView.setAdapter(questionAdapter);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != Activity.RESULT_OK || data == null)
//            return;
//
//        if(requestCode == REQUEST_CODE_QUIZ_ACTIVITY)
//
//    }

    private class QuestionHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewQuestion;
        private CheckBox mCheckBoxAnswer;
        private Question mQuestion;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewQuestion = itemView.findViewById(R.id.text_view_question);
            mCheckBoxAnswer = itemView.findViewById(R.id.checkBox_answer);
           // mQuestion = new Question(R.id.text_view_question , mCheckBoxAnswer.isChecked());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQuestion = new Question(mTextViewQuestion.getId() , mCheckBoxAnswer.isChecked());
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra(EXTRA_QUESTION_ID, mQuestion.getId());
//                    intent.putExtra(EXTRA_QUESTION_RES_ID,mQuestion.getQuestionTextResId());
//                    intent.putExtra(EXTRA_IS_TRUE_ANSWER, mQuestion.isAnswerTrue());
                    startActivity(intent);
                 //   startActivityForResult(intent , REQUEST_CODE_QUIZ_ACTIVITY);
                }
            });
        }
    }
        private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{

            private List<Question> mQuestions;

            public List<Question> getQuestions() {
                return mQuestions;
            }

            public void setQuestions(List<Question> questions) {
                mQuestions = questions;
            }

            public QuestionAdapter(List<Question> questions) {
                mQuestions = questions;
            }

            @Override
            public int getItemCount() {
                return mQuestions.size();
            }

            @NonNull
            @Override
            public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view = layoutInflater.inflate(R.layout.question_row_list, parent, false);
                QuestionHolder questionHolder = new QuestionHolder(view);
                return questionHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
                Question question = mQuestions.get(position);
                holder.mTextViewQuestion.setText(question.getQuestionTextResId());

                boolean answer = question.isAnswerTrue();
                if(answer)
                    holder.mCheckBoxAnswer.setChecked(true);
                else
                    holder.mCheckBoxAnswer.setChecked(false);
                mQuestion = new Question(question.getQuestionTextResId() , question.isAnswerTrue());
            }
        }
}