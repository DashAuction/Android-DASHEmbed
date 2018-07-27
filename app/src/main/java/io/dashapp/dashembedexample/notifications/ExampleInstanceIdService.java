package io.dashapp.dashembedexample.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.dashapp.dashembed.DASH;

public class ExampleInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("PUSH", "Refreshed token: " + refreshedToken);
        DASH.getInstance().setPushToken(refreshedToken);
    }
}
