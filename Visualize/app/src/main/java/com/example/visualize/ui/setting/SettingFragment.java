package com.example.visualize.ui.setting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visualize.ContentActivity;
import com.example.visualize.R;
import com.example.visualize.network.Response;
import com.example.visualize.user.User;

public class SettingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContentActivity activity = (ContentActivity) getContext();
        EditText usernameField = activity.findViewById(R.id.usernameField);
        final EditText passwordField = activity.findViewById(R.id.passwordField);
        Button updateButton = activity.findViewById(R.id.updateButton);


        final User user = activity.getUser();
        usernameField.setText(user.getUsername());

        updateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response update = user.update(passwordField.getText().toString());
                if (update.success()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Success")
                            .setMessage("Your profile has been successfully updated")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed")
                            .setMessage("Error: " + update.getStatusCode().toString())
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
            }
        });
    }
}