package com.example.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        String summary = getIntent().getStringExtra("summary");
        TextView summaryText = (TextView) findViewById(R.id.summary);
        summaryText.setText(summary);
    }

    public void goBack(View view) {
        Intent redirect = new Intent(Summary.this, MainActivity.class);
        startActivity(redirect);
    }
}