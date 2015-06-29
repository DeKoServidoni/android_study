package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main activity of the application
 *
 * Created by DeKoServidoni on 5/25/15.
 */
public class MainActivity extends Activity {

    private static final String SAVED_PASS = "password";
    private static final String SAVED_USER = "username";

    private EditText mUserInput = null;
    private EditText mPassInput = null;

    private SharedPreferences mPreference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String savedPass = mPreference.getString(SAVED_PASS, "");
        String savedUser = mPreference.getString(SAVED_USER, "");

        mUserInput = (EditText) findViewById(R.id.main_user_input);
        mPassInput = (EditText) findViewById(R.id.main_password_input);
        Button loginButton = (Button) findViewById(R.id.main_button_login);

        mUserInput.setText(savedUser);
        mPassInput.setText(savedPass);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = mUserInput.getText().toString();
                String password = mPassInput.getText().toString();

                if(username != null && username.equals("admin")
                        && password != null && password.equals("admin")) {

                    Toast.makeText(MainActivity.this, "Login OK!", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putString(SAVED_USER, username);
                    editor.putString(SAVED_PASS, password);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, CharactersActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou Senha incorreto!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
