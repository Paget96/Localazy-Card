package com.paget96.localazycard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class LocalazyCard extends MaterialCardView {

    // Variables
    private Context context;
    private LayoutInflater inflater;
    private View rootView;
    private TextView titleTextView, summaryTextView;
    private ImageView iconImageView;
    private MaterialButton translateButton;

    public LocalazyCard(final Context context) {
        this(context, null);
    }

    public LocalazyCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalazyCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        translateButton = rootView.findViewById(R.id.translate);

        setIcon(attributes.getResourceId(R.styleable.LocalazyCard_localazy_icon, R.drawable.ic_localazy));

        // Set title
        setTitle(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_title), context.getString(R.string.default_title)));

        // Set summary
        setSummaryText(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_summary), context.getString(R.string.default_summary)));

        // Open link on a button press
        setTranslateButton(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_app_translation_link), "https://localazy.com"));

        attributes.recycle();
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
}
