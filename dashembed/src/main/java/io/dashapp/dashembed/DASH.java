package io.dashapp.dashembed;

import android.app.Fragment;

public class DASH {

    private static DASH INSTANCE = null;
    private Config config;
    private String userEmail;
    private String pushSenderID;

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

    public void setPushSenderID(String senderID) {
        this.pushSenderID = senderID;
    }

    public Fragment dashFragment() {
        UserInfo userInfo = new UserInfo(pushSenderID, userEmail);
        Fragment dashFragment = DASHFragment.newInstance(config, userInfo);
        return dashFragment;
    }
}
