# DASH

## Purpose

This library provides an easy way to embed the DASH mobile experience into an Android application through a fragment. For more information check out [DASH](http://www.dashapp.io)

## Installation

Add DASHEmbed to your build.gradle file

```groovy
implementation 'io.dashapp.dashembed:dashembed:1.0.0@aar'
```

If you get an error, we might not be in the jcenter repository yet, go ahead and add our repository.

```groovy
maven {
    url "https://dl.bintray.com/dashdeveloper/maven"
}
```

## Usage

### Add required permissions

##### Fine location is used for future location based features.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

##### To enable push notification functionality

We will need your Android FCM (Firebase Cloud Messaging) API Key.

After receiving the push assets above, we will provide you with an App ID to use in the app.

### Create a Config

Create a Config. This is used to initialize the DASH library with needed information.

```java
Config config = new Config("APP_ID");
```

A second initializer allow you to use the DASH development servers to test.

```java
Config config;
if (isDebug) {
  config = Config("APP_ID", true);
} else {
  config = Config("APP_ID");
}
```

### Initialize the DASH library

Initialize the library with a context and the config.

```java
DASH.getInstance().startWithConfig(this, config);
```

### Set the push token to enable outbid notifications

```java
@Override
public void onTokenRefresh() {
    super.onTokenRefresh();

    // Get updated InstanceID token.
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    DASH.getInstance().setPushToken(refreshedToken);
}
```

### Handle extras from the push notification intent

```java
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    handleIntent(intent);
}

private void handleIntent(Intent intent) {
    //If we're reusing activities, handle the intent here, it will reload the DASH interface
    if (intent != null) {
        Bundle extras = intent.getExtras();
        if (DASH.getInstance().canHandlePushIntentExtras(extras)) {
            DASH.getInstance().setPushIntentExtras(extras);
        }
    }
}
```

### Set the user's email to enable autofill of email. DASH also determines if this user already has a DASH account

```java
//Set an email
String email = "test@email.com";
DASH.getInstance().setUserEmail(email);
```

### Get an instance of the DASHFragement and set a listener for errors

```java
DASHFragment fragment = DASH.getInstance().dashFragment();
final Context context = this;
fragment.setEventListener(new DASHFragmentEventListener() {
    @Override
    public void onReceivedError(Fragment dashFragment, int errorCode, String errorDescription) {
        if (currentAlertDialog != null) { return; }
        currentAlertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
                .setTitle("Error")
                .setMessage(errorDescription)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentAlertDialog = null;
                        dialogInterface.dismiss();
                    }
        });
        currentAlertDialog.show();
    }
});
```

### Display the  DASHFragment

```java
FragmentManager fragmentManager = getFragmentManager();

FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

fragmentTransaction
.replace(R.id.main_panel, fragment, "DASHFragment")
.addToBackStack("DASHFragment")
.commit();
fragmentManager.executePendingTransactions();
```

Check out the [Current Documentation](https://github.com/DashAuction/Android-DASHEmbed/raw/master/Documentation/DASHAuctions_V1.pdf)
