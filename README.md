# Localazy-Card
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) 
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FPaget96%2FLocalazy-Card&count_bg=%2379C83D&title_bg=%23555555&icon=talend.svg&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

This is a small library to help the developers who already using <a href="https://localazy.com">Localazy</a> to localize their apps

<img src="resources/screenshot_1.png" width="356" height="356"/>

Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
  
Include the library as a local library project or add the dependency in your build.gradle.
```
dependencies {
        implementation 'com.github.Paget96:Localazy-Card:v1.0.3'
}
```

# Setup
By the default library will set a default view, you can easily manipulate it over xml or inside class.

### Step 1
Include the view defined as below in your layout. And you can customize it like this.
```
   <com.paget96.localazycard.LocalazyCard
        android:id="@+id/localazyCard"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        app:localazy_app_translation_link="https://localazy.com"
        app:localazy_icon="@drawable/ic_localazy"
        app:localazy_invitation_message="@string/default_invitation_text"
        app:localazy_summary="@string/default_summary"
        app:localazy_title="@string/default_title" />
```

### Step 2
Or do the same inside your Activity or Fragment
```
LocalazyCard localazyCard = findViewById(R.id.localazyCard);
localazyCard.setIcon(R.drawable.ic_localazy);

// Title text
localazyCard.setTitle("Translate" + " " + getString(R.string.app_name));
localazyCard.setTitleTextSize(18f);
localazyCard.setTitleTextStyle(Typeface.BOLD);

// Summary text
localazyCard.setSummaryText("Help us to make " + getString(R.string.app_name) + " app multilingual, translate it to 50+ languages");
localazyCard.setSummaryTextSize(14f);
localazyCard.setSummaryTextStyle(Typeface.NORMAL);

// Open translation link
localazyCard.setTranslateButton("https://localazy.com");

// Invite to translate
localazyCard.setInviteButton(this, "I'm inviting you to help translating " + getString(R.string.app_name), "https://localazy.com");
```

### Step 3 (style)
Since this library is basically an extended view of a MaterialCardView, you can use pretty much the same features as the default material card have
```
localazyCard.setRadius(24); // Set card corner radius
localazyCard.setStrokeColor(ContextCompat.getColor(this, R.color.design_default_color_primary)); // Set stroke color
localazyCard.setStrokeWidth(2); // Set stroke width
localazyCard.setElevation(0f); // Set card elevation
// And much more
```

# Changelog
### 1.0.3 (20-Mar-2021)
- Added invitation button
- Slightly updated card view
- Updated app example

### 1.0.2 (19-Mar-2021)
- Added changing text size and style
