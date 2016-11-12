package com.toprogrammers.smartcamp.smartcamp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.toprogrammers.smartcamp.smartcamp.member.MemberMainActivity;
import com.toprogrammers.smartcamp.smartcamp.objects.InternetConnection;
import com.toprogrammers.smartcamp.smartcamp.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilg1101 on 12.11.2016.
 */
public class RegisterActivity extends AppCompatActivity {

    String name;
    String surname;
    String login;
    String password;
    String status;
    String response;

    EditText nameEditText;
    EditText surnameEditText;
    EditText loginEditText;
    EditText passwordEditText;

    Button registerButton;

    CheckBox checkBox;

    ProgressDialog progressDialog;

    InternetConnection ic;

    CardView registerCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ic = new InternetConnection();

        getSupportActionBar().setTitle("Регистрация");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        registerCardView = (CardView) findViewById(R.id.sign_up_button);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        registerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString();
                surname = surnameEditText.getText().toString();
                login = loginEditText.getText().toString();
                password = ic.md5Custom(passwordEditText.getText().toString());
                if (checkBox.isChecked()) {
                    status = String.valueOf(1);
                } else {
                    status = String.valueOf(0);
                }
                new UserRegisterTask().execute();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Загрузка...");
            progressDialog.setCancelable(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            response = ic.makeGETrequest(
                    new String[] {
                            "method", "user.signup",
                            "name", name,
                            "surname", surname,
                            "login", login,
                            "password", password,
                            "status", status
                    });

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {


            try {
                JSONObject json = new JSONObject(response);

                if (json.has("error")) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setMessage(json.getJSONObject("response").getString("error"))
                            .setCancelable(true)
                            .show();
                } else {
                    User user = new User(RegisterActivity.this, response);
                    user.updateInfo();
                    SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences("APP", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("userIsLoggedIn", true);
                    editor.commit();
                    if (user.getStatus() == 0) {
                        startActivity(new Intent(RegisterActivity.this, MemberMainActivity.class));
                        finish();
                    } else {
                        // open leader MainActivity
                    }
                }
            } catch (JSONException e) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("Ошибка!")
                        .setCancelable(true)
                        .show();
            }

            progressDialog.dismiss();
        }
    }
}
