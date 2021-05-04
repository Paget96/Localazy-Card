package com.paget96.localazy_card

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.paget96.localazycard.LocalazyCard
import com.paget96.localazycard.utils.LocaleUtils.onAttach

class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val localazyCard = findViewById<LocalazyCard>(R.id.localazyCard)

        // IMPORTANT, define activity for language changing
        localazyCard.setActivity(this)

        // Set card icon
        localazyCard.setIcon(R.drawable.ic_localazy_no_bgd)

        // Title text
        localazyCard.setTitle(getString(R.string.translate) + " " + getString(R.string.app_name))
        localazyCard.setTitleTextSize(18f)
        localazyCard.setTitleTextStyle(Typeface.BOLD)

        // Summary text
        localazyCard.setSummaryText(getString(R.string.summary_text, getString(R.string.app_name)))
        localazyCard.setSummaryTextSize(14f)
        localazyCard.setSummaryTextStyle(Typeface.NORMAL)

        // Open translation link
        localazyCard.setTranslateButton()

        // Invite to translate
        localazyCard.setInviteButton(getString(R.string.invitation_text, getString(R.string.app_name)))
        localazyCard.radius = 24f
        localazyCard.strokeColor = ContextCompat.getColor(this, R.color.design_default_color_primary)
        localazyCard.strokeWidth = 2
        localazyCard.elevation = 0f
    }
}