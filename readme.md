# DASH

## Purpose

This library provides an easy way to embed the DASH mobile experience into an Android application though a fragment.

## Usage

### Add required permissions

Fine location is used for future location based features.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

### Create a Config

Create a Config. This is used to initialize the DASH library with needeåd information.

```java
Config config = new Config("fcdallas", "hopscotch", "io.dashapp.dashembedexample");
```
### Initialize the DASH library

Initialize library either using the shared instance.

```java
DASH.getInstance().startWithConfig(config);
```

### Display the provided DASHFragment

```java
Fragment fragment = DASH.getInstance().dashFragement();
FragmentManager fragmentManager = getFragmentManager();

FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

fragmentTransaction
.replace(R.id.main_panel, fragment, "DASHFragment")
.addToBackStack("DASHFragment")
.commit();
fragmentManager.executePendingTransactions();
```

Check out the [Current Documentation](https://bitbucket.org/dashdev/dash-embed-android/raw/7343d6062f5d4b4508be739a56ca81c82adc574c/Documentation/DASHAuctions_V1.pdf)
