package com.paget96.localazycard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import com.localazy.android.Localazy;
import com.localazy.android.LocalazyLocale;
import com.paget96.localazycard.utils.LocaleUtils;
import com.paget96.localazycard.utils.UiUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocalazyCard extends MaterialCardView {

    // Variables
    private SharedPreferences preferences;
    private Context context;
    private LayoutInflater inflater;
    private View rootView;
    private TextView titleTextView, summaryTextView;
    private ImageView iconImageView, languageDownArrow;
    public LinearLayout selectLanguage, inviteButton, translateButton;
    private TextView language;
    private Uri localazyTranslationLink;
    private Activity activity;
    private Map<String, String> languagesInternal;
    private Map<LocalazyLocale, String> languagesLocalazy;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setLanguages(String language, String languageCountry) {
        languagesInternal.put(language, languageCountry);
    }

    private void setLanguages(LocalazyLocale language, String localeName) {
        languagesLocalazy.put(language, localeName);
    }

    public LocalazyCard(final Context context) {
        this(context, null);
    }

    public LocalazyCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalazyCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        languagesInternal = new HashMap<>();
        languagesLocalazy = new HashMap<>();
        localazyTranslationLink = Localazy.getProjectUri();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

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
        selectLanguage = rootView.findViewById(R.id.select_language);
        language = rootView.findViewById(R.id.language);
        languageDownArrow = rootView.findViewById(R.id.language_down_arrow);
        inviteButton = rootView.findViewById(R.id.invite);
        translateButton = rootView.findViewById(R.id.translate);

        if (!preferences.getBoolean("language_selected", false)) {
            selectLanguage.setBackgroundColor(ContextCompat.getColor(context, R.color.design_default_color_background));
            language.setText(context.getText(R.string.localazy_select_app_language));
            language.setTextColor(ContextCompat.getColor(context, R.color.no_language_selected_text_color));
            languageDownArrow.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.no_language_selected_text_color)));
        } else {
            LocalazyLocale currentLocale = Localazy.getCurrentLocalazyLocale();
            language.setText(currentLocale.getLocalizedName());
        }

        setIcon(attributes.getResourceId(R.styleable.LocalazyCard_localazy_icon, R.drawable.ic_localazy));

        // Set title
        setTitle(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_title), context.getString(R.string.localazy_default_title)));

        // Set summary
        setSummaryText(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_summary), context.getString(R.string.localazy_default_summary)));

        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLanguageInitialization();
    }

    private void setLanguageInitialization() {
        List<LocalazyLocale> locales = Localazy.getLocales();

        for (LocalazyLocale locale : locales) {
            // Returns display name for the locale in its own language - eq. "Čeština (Česko)" for "cs_CZ".
            String localizedName = locale.getLocalizedName();

            setLanguages(locale, localizedName);
        }

        if (languagesLocalazy != null) {
            if (languagesLocalazy.size() > 1) {
                selectLanguage.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.localazy_set_language_dialog_title));

                    LocalazyLocale[] langArray;
                    String[] langNameArray;
                    langArray = languagesLocalazy.keySet().toArray(new LocalazyLocale[0]);
                    langNameArray = languagesLocalazy.values().toArray(new String[0]);

                    String[] languageName = new String[langArray.length];
                    for (int i = 0; i < langArray.length; i++) {
                        languageName[i] = langArray[i].getLocalizedName();
                    }

                    builder.setItems(languageName, (dialog, which) -> {
                        language.setText(langArray[which].getLocalizedName());

                        preferences.edit().putBoolean("language_selected", true).apply();

                        Localazy.forceLocale(langArray[which].getLocale(), true);
                        activity.recreate();
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
            }
        }

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

    public void setTranslateButton() {
        translateButton.setOnClickListener(v -> UiUtils.openLink(context, localazyTranslationLink.toString()));
    }

    public void setInviteButton(String textMessage) {
        inviteButton.setVisibility(VISIBLE);
        inviteButton.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage + " " + localazyTranslationLink.toString());
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        });
    }
}
