# DASH

## Purpose

This library provides an easy way to embed the DASH mobile experience into an Android application through a fragment. For more information check out [DASH](http://www.dashapp.io)

## Usage

### Add required permissions

##### Fine location is used for future location based features.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

##### To enable push notification functionality

We will need your Android FCM (Firebase Cloud Messaging) API Key.

### Create a Config

Create a Config. This is used to initialize the DASH library with needed information.

```java
Config config = new Config("55e1bb99a1a135543f692bad");
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

Check out the [Current Documentation](https://bitbucket.org/dashdev/dash-embed-android/raw/7343d6062f5d4b4508be739a56ca81c82adc574c/Documentation/DASHAuctions_V1.pdf)
