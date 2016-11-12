package com.toprogrammers.smartcamp.smartcamp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.toprogrammers.smartcamp.smartcamp.member.MemberMainActivity;
import com.toprogrammers.smartcamp.smartcamp.objects.InternetConnection;
import com.toprogrammers.smartcamp.smartcamp.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    String login;
    String password;
    String response;

    EditText loginEditText;
    EditText passwordEditText;

    CardView signinButton;
    CardView signupButton;

    ProgressDialog progressDialog;

    InternetConnection ic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Авторизация");

        ic = new InternetConnection();

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        signinButton = (CardView) findViewById(R.id.sign_in_button);
        signupButton = (CardView) findViewById(R.id.sign_up_button);

        signinButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login = loginEditText.getText().toString();
                password = ic.md5Custom(passwordEditText.getText().toString());
                new UserLoginTask().execute();
            }
        });

        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Загрузка...");
            progressDialog.setCancelable(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            response = ic.makeGETrequest(
                    new String[] {
                            "method", "user.signin",
                            "login", login,
                            "password", password
                    });

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progressDialog.dismiss();

            try {
                JSONObject json = new JSONObject(response);

                if (json.has("error")) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(json.getJSONObject("response").getString("error"))
                            .setCancelable(true)
                            .show();
                } else {
                    User user = new User(LoginActivity.this, response);
                    user.updateInfo();
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("APP", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("userIsLoggedIn", true);
                    editor.commit();
                    if (user.getStatus() == 0) {
                        startActivity(new Intent(LoginActivity.this, MemberMainActivity.class));
                        finish();
                    } else {
                        // open leader MainActivity
                    }
                }
            } catch (JSONException e) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("Ошибка!")
                        .setCancelable(true)
                        .show();
            }
        }
    }
}

