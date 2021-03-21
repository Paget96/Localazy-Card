package com.paget96.localazycard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.paget96.localazycard.utils.LocaleUtils;
import com.paget96.localazycard.utils.UiUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalazyCard extends MaterialCardView {

    // Variables
    private Context context;
    private LayoutInflater inflater;
    private View rootView;
    private TextView titleTextView, summaryTextView;
    private ImageView iconImageView;
    private MaterialButton inviteButton, translateButton;
    private TextView language;
    private ImageView arrowDown;
    private LinearLayout selectLanguageLayout;
    private MaterialCardView selectLanguage;
    private Activity activity;
    private Map<String, String> languages;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setLanguages(String language, String languageCountry) {
        languages.put(language, languageCountry);
    }

    public LocalazyCard(final Context context) {
        this(context, null);
    }

    public LocalazyCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalazyCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        languages = new HashMap<>();

        initializeViews(context, attrs, defStyleAttr);
    }

    private void initializeViews(Context context, AttributeSet attrs, int defStyleAttr) {

        // Load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LocalazyCard, defStyleAttr, 0);
        this.context = context;

        inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.localazy_card_style1, this);

        titleTextView = rootView.findViewById(R.id.title_text);
        summaryTextView = rootView.findViewById(R.id.summary_text);
        iconImageView = rootView.findViewById(R.id.icon);
        inviteButton = rootView.findViewById(R.id.invite);
        translateButton = rootView.findViewById(R.id.translate);

        setIcon(attributes.getResourceId(R.styleable.LocalazyCard_localazy_icon, R.drawable.ic_localazy));

        // Set title
        setTitle(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_title), context.getString(R.string.localazy_default_title)));

        // Set summary
        setSummaryText(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_summary), context.getString(R.string.localazy_default_summary)));

        // Open link on a button press
        setTranslateButton(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_app_translation_link), "https://localazy.com"));

        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLanguageInitialization();
    }

    private void setLanguageInitialization() {
        selectLanguageLayout = rootView.findViewById(R.id.select_language_layout);
        selectLanguage = rootView.findViewById(R.id.select_language);
        language = rootView.findViewById(R.id.language);
        arrowDown = rootView.findViewById(R.id.expand_arrow);

        language.setText(LocaleUtils.getLanguage(context));

        if (languages != null) {
            if (languages.size() > 1) {
                rootView.setOnClickListener(v -> UiUtils.expandCollapseView(selectLanguageLayout, arrowDown));

                selectLanguage.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.localazy_set_language_dialog_title));

                    String[] langArray, langCountryArray;
                    langArray = languages.keySet().toArray(new String[0]);
                    langCountryArray = languages.values().toArray(new String[0]);
                    // for (Map.Entry<String, String> pair : languages.entrySet()) {

                    builder.setItems(langArray, (dialog, which) -> {
                        language.setText(langArray[which]);
                        LocaleUtils.setLocale(context, langArray[which], langCountryArray[which]);
                        activity.recreate();
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            } else
                arrowDown.setVisibility(GONE);

        } else
            arrowDown.setVisibility(GONE);

    }

    private String checkIsStringEmpty(String string, String defaultString) {
        if (string == null)
            return  defaultString;
        else if (string.isEmpty())
            return  defaultString;
        else return string;
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setTitleTextSize(float textSize) {
        titleTextView.setTextSize(textSize);
    }

    public void setTitleTextStyle(int typeface) {
        titleTextView.setTypeface(titleTextView.getTypeface(), typeface);
    }

    public void setSummaryText(String summaryText) {
        summaryTextView.setText(summaryText);
    }

    public void setSummaryTextStyle(int typeface) {
        summaryTextView.setTypeface(summaryTextView.getTypeface(), typeface);
    }

    public void setSummaryTextSize(float textSize) {
        summaryTextView.setTextSize(textSize);
    }

    public void setIcon(int icon) {
        iconImageView.setImageDrawable(ContextCompat.getDrawable(context, icon));
    }

    public void setTranslateButton(String url) {
        translateButton.setOnClickListener(v -> UiUtils.openLink(context, url));
    }

    public void setInviteButton(Context context, String textMessage, String url) {
        inviteButton.setVisibility(VISIBLE);
        inviteButton.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage + " " + url);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        });
    }
}
