package io.dashapp.dashembed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class DASH {

    private static final String NOTIFICATION_EXTRA_KEY = "dash";
    private static final String PREFERENCES_FILE_NAME = "dash_embed";
    private static final String PREFERENCE_PUSH_KEY = "push_key";
    private static final String PREFERENCE_EMAIL_KEY = "email_key";

    private static DASH INSTANCE = null;
    private Context context;
    private Config config;
    private String userEmail;
    private String pushToken;
    private String pushExtras;
    private WeakReference<DASHFragment> currentDASHFragmentReference;

    private DASH() {};

    public static DASH getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DASH();
        }
        return(INSTANCE);
    }

    public void startWithConfig(Context context, Config config) {
        this.context = context;
        this.config = config;

        //Pull the cached values from storage if present
        pushToken = cachedStringForKey(PREFERENCE_PUSH_KEY);
        userEmail = cachedStringForKey(PREFERENCE_EMAIL_KEY);
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
        cacheStringForKey(email, PREFERENCE_EMAIL_KEY);
    }

    public void clearUserEmail() {
        userEmail = null;
        cacheStringForKey(null, PREFERENCE_EMAIL_KEY);
    }

    public void setPushToken(String token) {
        this.pushToken = token;
        cacheStringForKey(token, PREFERENCE_PUSH_KEY);
    }

    public void clearPushToken() {
        pushToken = null;
        cacheStringForKey(null, PREFERENCE_PUSH_KEY);
    }

    public Boolean canHandlePushIntentExtras(Bundle extras) {
        if (extras == null) { return false; }
        return extras.containsKey(NOTIFICATION_EXTRA_KEY);
    }

    public void setPushIntentExtras(Bundle extras) {
        if (extras == null) { return; }
        pushExtras = extras.getString(NOTIFICATION_EXTRA_KEY);

        DASHFragment fragment = currentDASHFragmentReference.get();
        if (fragment != null) {
            fragment.updatePushIntentExtras(pushExtras);
        }
    }

    public Boolean hasNotificationData() {
        return pushExtras != null;
    }

    public void clearNotificationData() {
        pushExtras = null;
    }

    public DASHFragment dashFragment() {
        UserInfo userInfo = new UserInfo(pushToken, userEmail);
        DASHFragment dashFragment = DASHFragment.newInstance(config, userInfo, pushExtras);
        currentDASHFragmentReference = new WeakReference<>(dashFragment);
        return dashFragment;
    }

    private void cacheStringForKey(String string, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (string != null) {
            editor.putString(key, string);
        } else {
            editor.remove(key);
        }
        editor.apply();
    }

    private String cachedStringForKey(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }
}
