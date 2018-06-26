package io.dashapp.dashembed;

import android.app.Fragment;

public class DASH {

    private static DASH INSTANCE = null;
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

    public void startWithConfig(Config config) {
        this.config = config;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void setPushToken(String token) {
        this.pushToken = token;
    }

    public Fragment dashFragment() {
        UserInfo userInfo = new UserInfo(pushToken, userEmail);
        Fragment dashFragment = DASHFragment.newInstance(config, userInfo);
        return dashFragment;
    }
}
