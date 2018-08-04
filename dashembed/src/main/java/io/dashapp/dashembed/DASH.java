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

    /**
     * Singleton use of DASH.
     *
     * @return Shared instance of DASH
     */
    public static DASH getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DASH();
        }
        return(INSTANCE);
    }

    /**
     * Initializes DASH. Call this once before any other methods.
     *
     * @param context A context
     * @param config An instance of Config
     */
    public void startWithConfig(Context context, Config config) {
        this.context = context;
        this.config = config;

        //Pull the cached values from storage if present
        pushToken = cachedStringForKey(PREFERENCE_PUSH_KEY);
        userEmail = cachedStringForKey(PREFERENCE_EMAIL_KEY);
    }

    /**
     * Sets the current user's email. Email is used to uniquely identify a user in the DASH system.
     * Email is cached locally by DASH for ease of use.
     *
     * @param email The current user's email
     */
    public void setUserEmail(String email) {
        this.userEmail = email;
        cacheStringForKey(email, PREFERENCE_EMAIL_KEY);
    }

    /**
     *  Clears out local and cached user email data
     */
    public void clearUserEmail() {
        userEmail = null;
        cacheStringForKey(null, PREFERENCE_EMAIL_KEY);
    }

    /**
     * Used to set the push device token for the current app.
     * Set this with InstanceID token returned from the InstanceIDService.
     * This is used to send DASH outbid notifications on the team's behalf. Set each time token changes.
     * Token will be cached locally by DASH for ease of use.
     *
     * @param token The InstanceID token from the push service
     */
    public void setPushToken(String token) {
        this.pushToken = token;
        cacheStringForKey(token, PREFERENCE_PUSH_KEY);
    }

    /**
     *  Clears out local and cached push tokens in DASH
     */
    public void clearPushToken() {
        pushToken = null;
        cacheStringForKey(null, PREFERENCE_PUSH_KEY);
    }

    /**
     * Returns true if the intent extras passed in should be handled by DASH.
     * If true, you should tell DASH to handle the push and present the DASH fragment.
     * @param extras Intent extras on activity which handles push
     * @return If the intent extras passed in should be handled by DASH
     */
    public Boolean canHandlePushIntentExtras(Bundle extras) {
        if (extras == null) { return false; }
        return extras.containsKey(NOTIFICATION_EXTRA_KEY);
    }

    /**
     * Tells DASH to handle the push. The next presentation of the DASH fragment will handle the
     * notification. If DASH fragment is already presented, this will reload the interface to handle
     * the notification. EX: If the DASH outbid push intent extras are set here, the next
     * presentation will navigate directly to the respective auction item.
     *
     * @param extras Intent extras on activity which handles push
     */
    public void setPushIntentExtras(Bundle extras) {
        if (extras == null) { return; }
        pushExtras = extras.getString(NOTIFICATION_EXTRA_KEY);

        DASHFragment fragment = currentDASHFragmentReference.get();
        if (fragment != null) {
            fragment.updatePushIntentExtras(pushExtras);
        }
    }

    /**
     * Returns whether DASH currently has data as result of a push
     * (and subsequently DASH fragment should be presented)
     *
     * @return If DASH currently has push data
     */
    public Boolean hasNotificationData() {
        return pushExtras != null;
    }

    /**
     * Clears out the pending notification data
     */
    public void clearNotificationData() {
        pushExtras = null;
    }

    /**
     * Returns a DASH fragment for display.
     * @return DASH fragment for display
     */
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
