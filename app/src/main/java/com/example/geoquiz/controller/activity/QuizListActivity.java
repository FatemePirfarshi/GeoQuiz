package com.example.geoquiz.controller.activity;

import androidx.fragment.app.Fragment;

import com.example.geoquiz.controller.fragment.QuizListFragment;

public class QuizListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }
}