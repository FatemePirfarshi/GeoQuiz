package com.example.geoquiz.controller.activity;

import androidx.fragment.app.Fragment;

import com.example.geoquiz.controller.fragment.QuizeDefaultFragment;

public class QuizActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new QuizeDefaultFragment();
    }
}