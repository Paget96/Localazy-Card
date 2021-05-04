package com.paget96.localazycard

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.material.card.MaterialCardView
import com.localazy.android.Localazy
import com.localazy.android.LocalazyLocale
import com.paget96.localazycard.utils.UiUtils.openLink
import java.util.*

class LocalazyCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MaterialCardView(context, attrs, defStyleAttr) {
    // Variables
    private val preferences: SharedPreferences
    private var titleTextView: TextView? = null
    private var summaryTextView: TextView? = null
    private var iconImageView: ImageView? = null
    private var languageDownArrow: ImageView? = null
    var selectLanguage: LinearLayout? = null
    var inviteButton: LinearLayout? = null
    var translateButton: LinearLayout? = null
    private var language: TextView? = null
    private val localazyTranslationLink: Uri
    private var activity: Activity? = null
    private val languagesInternal: MutableMap<String, String>
    private val languagesLocalazy: MutableMap<LocalazyLocale, String>?

    fun setActivity(activity: Activity?) {
        this.activity = activity
    }

    fun setLanguages(language: String, languageCountry: String) {
        languagesInternal[language] = languageCountry
    }

    private fun setLanguages(language: LocalazyLocale, localeName: String) {
        languagesLocalazy!![language] = localeName
    }

    private fun initializeViews(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        // Load the styled attributes and set their properties
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LocalazyCard, defStyleAttr, 0)

        val cardRootView: View? = LayoutInflater.from(context).inflate(R.layout.localazy_card_style1, this)

        titleTextView = cardRootView?.findViewById(R.id.title_text)
        summaryTextView = cardRootView?.findViewById(R.id.summary_text)
        iconImageView = cardRootView?.findViewById(R.id.icon)
        selectLanguage = cardRootView?.findViewById(R.id.select_language)
        language = cardRootView?.findViewById(R.id.language)
        languageDownArrow = cardRootView?.findViewById(R.id.language_down_arrow)
        inviteButton = cardRootView?.findViewById(R.id.invite)
        translateButton = cardRootView?.findViewById(R.id.translate)

        if (!preferences.getBoolean("language_selected", false)) {
            selectLanguage?.setBackgroundColor(ContextCompat.getColor(context, R.color.design_default_color_background))
            language?.setText(context.getText(R.string.localazy_select_app_language))
            language?.setTextColor(ContextCompat.getColor(context, R.color.no_language_selected_text_color))
            languageDownArrow?.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.no_language_selected_text_color)))

        } else {
            val currentLocale = Localazy.getCurrentLocalazyLocale()
            language?.setText(currentLocale.getLocalizedName())
        }

        setIcon(attributes.getResourceId(R.styleable.LocalazyCard_localazy_icon, R.drawable.ic_localazy))

        // Set title
        setTitle(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_title), context.getString(R.string.localazy_default_title)))

        // Set summary
        setSummaryText(checkIsStringEmpty(attributes.getString(R.styleable.LocalazyCard_localazy_summary), context.getString(R.string.localazy_default_summary)))
        attributes.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLanguageInitialization()
    }

    private fun setLanguageInitialization() {
        val locales = Localazy.getLocales()

        for (locale in locales) {
            // Returns display name for the locale in its own language - eq. "Čeština (Česko)" for "cs_CZ".
            val localizedName = locale.getLocalizedName()
            setLanguages(locale, localizedName)
        }

        if (languagesLocalazy != null) {
            if (languagesLocalazy.size > 1) {
                selectLanguage!!.setOnClickListener {

                    val builder = AlertDialog.Builder(context!!)
                    builder.setTitle(context!!.getString(R.string.localazy_set_language_dialog_title))
                    val langArray: Array<LocalazyLocale>
                    val langNameArray: Array<String>
                    langArray = languagesLocalazy.keys.toTypedArray()
                    langNameArray = languagesLocalazy.values.toTypedArray()

                    val languageName = arrayOfNulls<String>(langArray.size)
                    for (i in langArray.indices) {
                        languageName[i] = langArray[i].getLocalizedName()
                    }

                    builder.setItems(languageName) { dialog: DialogInterface?, which: Int ->
                        preferences.edit().putBoolean("language_selected", true).apply()
                        Localazy.forceLocale(langArray[which].locale, true)
                        activity!!.recreate()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    private fun checkIsStringEmpty(string: String?, defaultString: String): String {
        return if (string == null) defaultString else if (string.isEmpty()) defaultString else string
    }

    fun setTitle(title: String?) {
        titleTextView!!.text = title
    }

    fun setTitleTextSize(textSize: Float) {
        titleTextView!!.textSize = textSize
    }

    fun setTitleTextStyle(typeface: Int) {
        titleTextView!!.setTypeface(titleTextView!!.typeface, typeface)
    }

    fun setSummaryText(summaryText: String?) {
        summaryTextView!!.text = summaryText
    }

    fun setSummaryTextStyle(typeface: Int) {
        summaryTextView!!.setTypeface(summaryTextView!!.typeface, typeface)
    }

    fun setSummaryTextSize(textSize: Float) {
        summaryTextView!!.textSize = textSize
    }

    fun setIcon(icon: Int) {
        iconImageView!!.setImageDrawable(ContextCompat.getDrawable(context!!, icon))
    }

    fun setTranslateButton() {
        translateButton!!.setOnClickListener { v: View? -> openLink(context!!, localazyTranslationLink.toString()) }
    }

    fun setInviteButton(textMessage: String) {
        inviteButton!!.visibility = VISIBLE
        inviteButton!!.setOnClickListener { v: View? ->
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "$textMessage $localazyTranslationLink")
            sendIntent.type = "text/plain"
            activity!!.startActivity(sendIntent)
        }
    }

    init {
        languagesInternal = HashMap()
        languagesLocalazy = HashMap()
        localazyTranslationLink = Localazy.getProjectUri()
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        initializeViews(context, attrs, defStyleAttr)
    }
}