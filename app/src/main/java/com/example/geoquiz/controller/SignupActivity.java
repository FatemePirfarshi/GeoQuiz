package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    public static final String EXTRA_SIGNUP_USERNAME = "com.example.register.signupUsername";
    public static final String EXTRA_SIGNUP_PASSWORD = "com.example.register.signupPassword";
    public static final String BUNDLE_SIGNUP_USERNAME = "signupUsername";
    public static final String BUNDLE_SIGNUP_PASSWORD = "signupPassword";
    private TextInputLayout mEditTextSignUpUserName;
    private TextInputLayout mEditTextSignUpPassword;
    private Button mButtonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        findViews();

        mEditTextSignUpUserName.getEditText().setText(getIntent().getStringExtra(LoginActivity.EXTRA_LOG_IN_USER_NAME));
        mEditTextSignUpPassword.getEditText().setText(getIntent().getStringExtra(LoginActivity.EXTRA_LOG_IN_PASSWORD));

        if(savedInstanceState != null){
            mEditTextSignUpUserName.getEditText().setText(savedInstanceState.getString(BUNDLE_SIGNUP_USERNAME));
            mEditTextSignUpPassword.getEditText().setText(savedInstanceState.getString(BUNDLE_SIGNUP_PASSWORD));
        }

        setListeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_SIGNUP_USERNAME, mEditTextSignUpUserName.getEditText().getText().toString());
        outState.putString(BUNDLE_SIGNUP_PASSWORD, mEditTextSignUpPassword.getEditText().getText().toString());
    }

    private void findViews() {
        mEditTextSignUpUserName = findViewById(R.id.edittxt_signup_username);
        mEditTextSignUpPassword = findViewById(R.id.edittxt_signup_password);
        mButtonSignup = findViewById(R.id.btn_signup);
    }

    private void setListeners() {

        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra(EXTRA_SIGNUP_USERNAME, mEditTextSignUpUserName.getEditText().getText().toString());
                intent.putExtra(EXTRA_SIGNUP_PASSWORD, mEditTextSignUpPassword.getEditText().getText().toString());
                setResult(RESULT_OK, intent);

                if (mEditTextSignUpUserName.getEditText().getText().toString().trim().isEmpty()) {

                    mEditTextSignUpUserName.setErrorEnabled(true);
                    mEditTextSignUpUserName.setError("Field cannot be empty!");

                    Toast.makeText(SignupActivity.this, R.string.empty_edittext_username,
                            Toast.LENGTH_LONG).show();
                }
                else if (mEditTextSignUpPassword.getEditText().getText().toString().length() == 0)
                    Toast.makeText(SignupActivity.this, R.string.empty_edittext_password,
                            Toast.LENGTH_LONG).show();

                else
                    finish();
            }
        });
    }
    private boolean validateInput() {
        if (mEditTextSignUpUserName.getEditText().getText().toString().trim().isEmpty()) {
            mEditTextSignUpUserName.setErrorEnabled(true);
            mEditTextSignUpUserName.setError("Field cannot be empty!");
            return false;
        }
        mEditTextSignUpUserName.setErrorEnabled(false);
        return true;
    }

}