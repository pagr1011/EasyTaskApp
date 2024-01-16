package com.app.e_business_easytask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackText;
    private Button submitButton;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        feedbackText = (EditText) findViewById(R.id.feedbackText);
        submitButton = (Button) findViewById(R.id.submitButton);
        closeButton = findViewById(R.id.closeButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackText.getText().toString();
                float rating = ratingBar.getRating();

                // Hier können Sie die Bewertung und das Feedback verarbeiten
                Toast.makeText(FeedbackActivity.this, "Bewertung: " + rating + "\nFeedback: " + feedback, Toast.LENGTH_SHORT).show();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Schließe die Aktivität
            }
        });
    }
}