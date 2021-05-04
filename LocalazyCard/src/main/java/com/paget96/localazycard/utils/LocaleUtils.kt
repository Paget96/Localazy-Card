package com.paget96.localazycard.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.preference.PreferenceManager
import java.util.*

object LocaleUtils {
    private const val TAG = "localazy_locale"
    private const val SELECTED_LANGUAGE = "en"
    private const val SELECTED_LANGUAGE_COUNTRY = "US"
    @JvmStatic
    fun onAttach(context: Context): Context {
        Log.d(TAG, "onAttach:")
        val lang = getPersistedData(context, Locale.getDefault().language)
        val langCountry = getPersistedCountryData(context, Locale.getDefault().country)
        return setLocale(context, lang, langCountry)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun getLanguageCountry(context: Context): String? {
        return getPersistedCountryData(context, Locale.getDefault().country)
    }

    fun setLocale(context: Context, language: String?, langCountry: String?): Context {
        Log.d(TAG, "setLocale:  ")
        persist(context, language, langCountry)
        return updateResources(context, language, langCountry)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun getPersistedCountryData(context: Context, defaultLangCountry: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE_COUNTRY, defaultLangCountry)
    }

    private fun persist(context: Context, language: String?, langCountry: String?) {
        Log.d(TAG, "persist:  ")
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.putString(SELECTED_LANGUAGE_COUNTRY, langCountry)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?, langCountry: String?): Context {
        Log.d(TAG, "updateResources:  ")
        val locale = Locale(language, langCountry)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}