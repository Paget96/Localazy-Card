package com.paget96.localazy_card;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.paget96.localazycard.LocalazyCard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalazyCard localazyCard = findViewById(R.id.localazyCard);

        localazyCard.setIcon(R.drawable.ic_localazy);
        localazyCard.setTitle(getString(R.string.default_title));
        localazyCard.setSummaryText(getString(R.string.default_summary));
        localazyCard.setTranslateButton("https://localazy.com");
    }
}