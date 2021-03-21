package com.paget96.localazy_card;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.paget96.localazycard.LocalazyCard;
import com.paget96.localazycard.utils.LocaleUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtils.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalazyCard localazyCard = findViewById(R.id.localazyCard);

        // Optional - define what languages your app have
        // this will be used for language changer
        // IMPORTANT, define activity for language changing
        localazyCard.setActivity(this);
        localazyCard.setLanguages("en", "");
        localazyCard.setLanguages("de", "");
        localazyCard.setLanguages("fr", "");

        // Set card icon
        localazyCard.setIcon(R.drawable.ic_localazy);

        // Title text
        localazyCard.setTitle(getString(R.string.translate) + " " + getString(R.string.app_name));
        localazyCard.setTitleTextSize(18f);
        localazyCard.setTitleTextStyle(Typeface.BOLD);

        // Summary text
        localazyCard.setSummaryText(getString(R.string.summary_text, getString(R.string.app_name)));
        localazyCard.setSummaryTextSize(14f);
        localazyCard.setSummaryTextStyle(Typeface.NORMAL);

        // Open translation link
        localazyCard.setTranslateButton("https://localazy.com");

        // Invite to translate
        localazyCard.setInviteButton(this, getString(R.string.invitation_text, getString(R.string.app_name)) , "https://localazy.com");

        localazyCard.setRadius(24f);
        localazyCard.setStrokeColor(ContextCompat.getColor(this, R.color.design_default_color_primary));
        localazyCard.setStrokeWidth(2);
        localazyCard.setElevation(0f);
    }
}