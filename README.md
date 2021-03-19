# Localazy-Card

This is a small library to help the developers who already using <a href="https://localazy.com">Localazy</a> to localize their apps

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
        implementation 'com.github.Paget96:Localazy-Card:Tag'
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
        app:localazy_summary="@string/default_summary"
        app:localazy_title="@string/default_title" />
```

### Step 2
Or do the same inside your Activity or Fragment
```
LocalazyCard localazyCard = findViewById(R.id.localazyCard);

// Card icon
localazyCard.setIcon(R.drawable.ic_localazy);

// Title text
localazyCard.setTitle(getString(R.string.default_title) + " " + "AppName");
localazyCard.setTitleTextSize(18f);
localazyCard.setTitleTextStyle(Typeface.BOLD);

// Summary text
localazyCard.setSummaryText(getString(R.string.default_summary));
localazyCard.setSummaryTextSize(14f);
localazyCard.setSummaryTextStyle(Typeface.NORMAL);

// Button link
localazyCard.setTranslateButton("https://localazy.com");
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
