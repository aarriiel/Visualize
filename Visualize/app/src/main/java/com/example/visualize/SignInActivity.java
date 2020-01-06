package com.example.visualize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visualize.network.Response;
import com.example.visualize.user.User;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signIn() {
        TextView errorMessage = findViewById(R.id.errorMessage);
        String username = ((EditText) findViewById(R.id.usernameField)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();
        try {
            Response signIn = User.login(username, password);

            switch (signIn.getStatusCode()) {
                case SUCCESS: {
                    Intent intent = new Intent(SignInActivity.this, ContentActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                    break;
                }
                case VALIDATION_ERR: {
                    errorMessage.setText("Incorrect username/password");
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
}
