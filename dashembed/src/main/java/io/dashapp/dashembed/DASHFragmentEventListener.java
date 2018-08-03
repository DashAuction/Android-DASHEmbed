package io.dashapp.dashembed;

import android.app.Fragment;
import android.webkit.WebResourceError;

public interface DASHFragmentEventListener {
    //Error codes are WebViewClient Error codes
    void onReceivedError(Fragment dashFragment, int errorCode, String errorDescription);
}
