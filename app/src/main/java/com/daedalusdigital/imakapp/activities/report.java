package com.daedalusdigital.imakapp.activities;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.daedalusdigital.imakapp.R;
import com.daedalusdigital.imakapp.SpreadsheetWebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class report  extends Fragment {

    private EditText feedbackInputField;
    private EditText nameInputField;
    private EditText emailInputField;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.report, container, false);

        collapsingToolbar =v.findViewById(R.id.collapsing);
        collapsingToolbar.setTitle("Your Feedback");

        feedbackInputField = v.findViewById(R.id.feedback_input);
        nameInputField =v.findViewById(R.id.name_input);
        emailInputField =v.findViewById(R.id.email_input);

        v.findViewById(R.id.submit_button).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        validateInput();
                    }
                }
        );

        return v;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    private void validateInput() { // Validate text input

        if (feedbackInputField.getText().toString().trim().length() == 0 && nameInputField.getText().toString().trim().length() == 0 && emailInputField.getText().toString().trim().length() == 0) {
            feedbackInputField.setError("Enter your feedback!");
            nameInputField.setError("Enter a valid name!");
            emailInputField.setError("Enter a valid email!");
        }
    }

    private void sendData() { // Send feedback to Google Spreadsheet if text input is valid

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/") // Your spreadsheet URL
                .build();
        final SpreadsheetWebService spreadsheetWebService = retrofit.create(SpreadsheetWebService.class);

        String feedbackInput = feedbackInputField.getText().toString();
        String nameInput = nameInputField.getText().toString();
        String emailInput = emailInputField.getText().toString();

        Call<Void> feedbackCall = spreadsheetWebService.feedbackSend(feedbackInput, nameInput, emailInput);
        feedbackCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("XXX", "Submitted. " + response);

                // Clear all fields after submitting
                feedbackInputField.setText("");
                nameInputField.setText("");
                emailInputField.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("XXX", "Failed", t);

            }
        });
    }

}
