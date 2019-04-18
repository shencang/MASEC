package com.shencangblue.jin.forceofflineandrememberpassworddemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    Button loginBtn;
    EditText usernameET, passwordET;
    CheckBox forgetPasswordCB;
    TextView headTV;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        headTV = (TextView) findViewById(R.id.headTV);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                if (username.equals("admin") && password.equals("123456")) {

                    if (forgetPasswordCB.isChecked()) {
                        editor.putString("usernameET", username);
                        editor.putString("passwordET", password);
                        editor.putBoolean("forgetPasswordCB", forgetPasswordCB.isChecked());

                    }else {
                        editor.clear();

                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    headTV.setText("登陆成功");


                } else {
                    Toast.makeText(LoginActivity.this,
                            "您输入的用户名或者密码有误，请重新输入！"
                            , Toast.LENGTH_LONG).show();

                    usernameET.setText("");
                    passwordET.setText("");
                    headTV.setText("登陆失败");
                }
            }
        });
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        forgetPasswordCB = (CheckBox) findViewById(R.id.rememberPasswordCB);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        boolean isForget = sharedPreferences.getBoolean("forgetPasswordCB", false);
        if (isForget) {
            String userNameStr = sharedPreferences.getString("usernameET", "");
            String userPasswordStr = sharedPreferences.getString("passwordET", "");
            usernameET.setText(userNameStr);
            passwordET.setText(userPasswordStr);
            forgetPasswordCB.setChecked(true);
        }
    }
}
