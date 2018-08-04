package io.dashapp.dashembed;

import android.app.Fragment;

public interface DASHFragmentEventListener {

    /**
     * Called each time an error occurs in the DASH fragment
     *
     * @param dashFragment The DASH fragment that had the error
     * @param errorCode The errorCode (passed through from WebViewClient)
     * @param errorDescription A description of the error
     */
    void onReceivedError(Fragment dashFragment, int errorCode, String errorDescription);
}
