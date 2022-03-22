package com.csc498.groupCurrencyConverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ConversionActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        text = (TextView)findViewById(R.id.textView);

        Intent i = getIntent();
        String s = i.getStringExtra("conversion_type");
        text.setText(s);
    }
}