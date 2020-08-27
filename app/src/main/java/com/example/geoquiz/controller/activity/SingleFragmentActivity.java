package com.example.geoquiz.controller.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.geoquiz.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
    }
}
