package com.paget96.localazycard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class LocalazyCard extends MaterialCardView {

    // Variables
    private LayoutInflater inflater;
    private View rootView;
    private TextView title;

    public LocalazyCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(R.layout.localazy_card, this);

        initializeViews();
    }

    private void initializeViews() {
        title = rootView.findViewById(R.id.title1);
        title.setText("yoyo");
    }
}
