package com.example.visualize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visualize.network.Response;
import com.example.visualize.user.User;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void signUp() {
        TextView errorMessage = findViewById(R.id.errorMessage);
        if (!isFormValid()) {
            errorMessage.setText("Please fill out all required fields");
            return;
        }

        String username = ((EditText) findViewById(R.id.usernameField)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();

        try {
            Response signUp = User.register(username, password);

            switch (signUp.getStatusCode()) {
                case SUCCESS: {
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    finish();
                    break;
                }
                case ALREADY_REGISTERED: {
                    errorMessage.setText("This username has already been registered");
                    break;
                }
                default:
                    break;
            }
        } catch (Exception ex) {
            errorMessage.setText("Unable to connect to server");
            ex.printStackTrace();
        }
    }

    private boolean isFormValid() {
        String username = ((EditText) findViewById(R.id.usernameField)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();
        return !username.isEmpty() && !password.isEmpty();
    }

}