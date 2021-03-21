package com.paget96.localazycard.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AndroidRuntimeException;
import android.view.View;

public class UiUtils {

    // Parses and open links.
    public static void openLink(Context context, String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void expandView(View expandedLayout, View animateArrow) {
        try {
            animateArrow.animate().rotation(180).setDuration(500).start();
        } catch (AndroidRuntimeException are) {
            are.printStackTrace();
        }
        expandedLayout.setVisibility(View.VISIBLE);
    }

    public static void collapseView(View expandedLayout, View animateArrow) {
        try {
            animateArrow.animate().rotation(0).setDuration(500).start();
        } catch (AndroidRuntimeException are) {
            are.printStackTrace();
        }
        expandedLayout.setVisibility(View.GONE);
    }

    public static void expandCollapseView(View expandedLayout, View animateArrow) {
        if (expandedLayout.isShown()) {
            collapseView(expandedLayout, animateArrow);
        } else {
            expandView(expandedLayout, animateArrow);
        }
    }

}
