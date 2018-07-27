package io.dashapp.dashembed;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;

public class DASH {

    private static final String PREFERENCES_FILE_NAME = "dash_embed";
    private static final String PREFERENCE_PUSH_KEY = "push_key";

    private static DASH INSTANCE = null;
    private Context context;
    private Config config;
    private String userEmail;
    private String pushToken;

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

        //Pull the push token from storage if present
        pushToken = cachedPushToken();
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void setPushToken(String token) {
        this.pushToken = token;
        cachePushToken(token);
    }

    public Fragment dashFragment() {
        UserInfo userInfo = new UserInfo(pushToken, userEmail);
        Fragment dashFragment = DASHFragment.newInstance(config, userInfo);
        return dashFragment;
    }

    private void cachePushToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PREFERENCE_PUSH_KEY, token);
        editor.commit();
    }

    private String cachedPushToken() {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(PREFERENCE_PUSH_KEY, null);
    }
}
