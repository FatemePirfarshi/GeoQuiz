package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_LOG_IN_USER_NAME = "com.example.register.LogInUserName";
    public static final String EXTRA_LOG_IN_PASSWORD = "com.example.register.LogInPassword";
    public static final int REQUEST_CODE_SIGN_UP = 0;
    public static final String BUNDLE_LOGIN_USERNAME = "loginUsername";
    public static final String BUNDLE_LOGIN_PASSWORD = "loginPassword";
    public static final String BUNDLE_SIGN_UP_USERNAME = "bundleSignUpUsername";
    public static final String BUNDLE_SIGN_UP_PASSWORD = "bundleSignUpPassword";
    public static final String LOGIN_USERNAME = "login username";
    public static final String LOGIN_PASSWORD = "login password";
    private TextInputLayout mEditTextUserName;
    private TextInputLayout mEditTextPassword;
    private Button mButtonLogIn;
    private Button mButtonSignUp;
    private LinearLayout mLinearLayoutRoot;
    private FrameLayout mFrameLayoutRoot;

    private String signupUsername;
    private String signupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();

        if (savedInstanceState != null) {
            mEditTextUserName.getEditText().setText(savedInstanceState.getString(BUNDLE_LOGIN_USERNAME));
            mEditTextPassword.getEditText().setText(savedInstanceState.getString(BUNDLE_LOGIN_PASSWORD));
            signupUsername = savedInstanceState.getString(BUNDLE_SIGN_UP_USERNAME);
            signupPassword = savedInstanceState.getString(BUNDLE_LOGIN_PASSWORD);
        }

        setListeners();
    }

    private void findViews() {
        mEditTextUserName = findViewById(R.id.edittxt_username);
        mEditTextPassword = findViewById(R.id.edittxt_password);
        mButtonLogIn = findViewById(R.id.btn_log_in);
        mButtonSignUp = findViewById(R.id.btn_sign_up);
        mLinearLayoutRoot = findViewById(R.id.root_layout);
        mFrameLayoutRoot = findViewById(R.id.root_frame_layout);
    }

    private void setListeners() {

        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showResult();
                Intent intent = new Intent();
                intent.putExtra(LOGIN_USERNAME, mEditTextUserName.getEditText().getText().toString());
                intent.putExtra(LOGIN_PASSWORD, mEditTextPassword.getEditText().getText().toString());
                setResult(RESULT_OK , intent);

                finish();
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);

                intent.putExtra(EXTRA_LOG_IN_USER_NAME, mEditTextUserName.getEditText().getText().toString());
                intent.putExtra(EXTRA_LOG_IN_PASSWORD, mEditTextPassword.getEditText().getText().toString());

                startActivityForResult(intent, REQUEST_CODE_SIGN_UP);

                validateInput();
            }
        });
    }

    private void showResult() {
        if (mEditTextUserName.getEditText().getText().toString().equals(signupUsername)
                && mEditTextPassword.getEditText().getText().toString().equals(signupPassword)) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                Snackbar.make(mLinearLayoutRoot, "Submited !!!", Snackbar.LENGTH_LONG).show();
            else
                Snackbar.make(mFrameLayoutRoot, "Submited !!!", Snackbar.LENGTH_LONG).show();
        } else
            Toast.makeText(LoginActivity.this, R.string.invalid_info,
                    Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_SIGN_UP) {
            signupUsername = data.getStringExtra(SignupActivity.EXTRA_SIGNUP_USERNAME);
            mEditTextUserName.getEditText().setText(signupUsername);
            signupPassword = data.getStringExtra(SignupActivity.EXTRA_SIGNUP_PASSWORD);
            mEditTextPassword.getEditText().setText(signupPassword);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_SIGN_UP_USERNAME, signupUsername);
        outState.putString(BUNDLE_SIGN_UP_PASSWORD, signupPassword);
        outState.putString(BUNDLE_LOGIN_USERNAME, mEditTextUserName.getEditText().getText().toString());
        outState.putString(BUNDLE_LOGIN_PASSWORD, mEditTextPassword.getEditText().getText().toString());
    }

    private boolean validateInput() {
        if (mEditTextUserName.getEditText().getText().toString().trim().isEmpty()) {
            mEditTextUserName.setErrorEnabled(true);
            mEditTextUserName.setError("Field isnot valid!");
            return false;
        }
        mEditTextUserName.setErrorEnabled(false);
        return true;
    }
}