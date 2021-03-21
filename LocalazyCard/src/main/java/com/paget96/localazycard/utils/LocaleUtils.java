package com.paget96.localazycard.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

public class LocaleUtils {

    private static final String TAG = "localazy_locale";
    private static final String SELECTED_LANGUAGE = "en";
    private static final String SELECTED_LANGUAGE_COUNTRY = "US";


    public static Context onAttach(Context context) {
        Log.d(TAG, "onAttach:");
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        String langCountry = getPersistedCountryData(context, Locale.getDefault().getCountry());
        return setLocale(context, lang, langCountry);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static String getLanguageCountry(Context context) {
        return getPersistedCountryData(context, Locale.getDefault().getCountry());
    }

    public static Context setLocale(Context context, String language, String langCountry) {
        Log.d(TAG, "setLocale:  ");
        persist(context, language, langCountry);

        return updateResources(context, language, langCountry);


    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static String getPersistedCountryData(Context context, String defaultLangCountry) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE_COUNTRY, defaultLangCountry);
    }

    private static void persist(Context context, String language, String langCountry) {
        Log.d(TAG, "persist:  ");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.putString(SELECTED_LANGUAGE_COUNTRY, langCountry);
        editor.apply();
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language, String langCountry) {
        Log.d(TAG, "updateResources:  ");
        Locale locale = new Locale(language, langCountry);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }
}
