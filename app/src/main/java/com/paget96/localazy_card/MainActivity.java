package com.paget96.localazy_card;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.paget96.localazycard.LocalazyCard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalazyCard localazyCard = findViewById(R.id.localazyCard);
        localazyCard.setIcon(R.drawable.ic_localazy);

        // Title text
        localazyCard.setTitle(getString(R.string.default_title) + " " + getString(R.string.app_name));
        localazyCard.setTitleTextSize(18f);
        localazyCard.setTitleTextStyle(Typeface.BOLD);

        // Summary text
        localazyCard.setSummaryText(getString(R.string.default_summary));
        localazyCard.setSummaryTextSize(14f);
        localazyCard.setSummaryTextStyle(Typeface.NORMAL);

        // Button link
        localazyCard.setTranslateButton("https://localazy.com");

        localazyCard.setRadius(24f);
        localazyCard.setStrokeColor(ContextCompat.getColor(this, R.color.design_default_color_primary));
        localazyCard.setStrokeWidth(2);
        localazyCard.setElevation(0f);
    }
}