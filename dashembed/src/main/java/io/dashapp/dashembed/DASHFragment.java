package io.dashapp.dashembed;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DASHFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DASHFragment extends Fragment {
    private static final String ARG_CONFIG = "config";
    private static final String ARG_USER_INFO = "userInfo";
    private static final String ARG_PUSH_EXTRAS = "pushExtras";
    private static final String URL_SCHEME = "https";
    private static final String URL_AUTHORITY = "dev-web.dashapp.io";
    private static final String URL_PATH_APP = "app";

    private static final String QUERY_PLATFORM = "platformId";
    private static final String QUERY_VALUE_ANDROID = "android";
    private static final String QUERY_APPLICATION = "appId";
    private static final String QUERY_EMAIL = "email";
    private static final String QUERY_PUSH = "pushId";

    private Config config;
    private UserInfo userInfo;
    private String pushExtras;
    private WebView webView;

    public DASHFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param config The config to use for initializing the fragment.
     * @param userInfo The user information used by the fragment.
     * @return A new instance of fragment DASHFragment.
     */
    public static DASHFragment newInstance(Config config, UserInfo userInfo, String pushExtras) {
        DASHFragment fragment = new DASHFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONFIG, config);
        args.putParcelable(ARG_USER_INFO, userInfo);
        args.putString(ARG_PUSH_EXTRAS, pushExtras);
        fragment.setArguments(args);
        return fragment;
    }

    /// Updates the fragment with notification data. Used internally.
    public void updatePushIntentExtras(String pushExtras) {
        this.pushExtras = pushExtras;
        reloadInterface(true);
    }

    /// Refreshes the current page. If startFromBeginning is true, the interface is reloaded to the beginning state.
    public void reloadInterface(boolean startFromBeginning) {
        if (startFromBeginning) {
            loadWebView();
        } else {
            webView.reload();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            config = getArguments().getParcelable(ARG_CONFIG);
            userInfo = getArguments().getParcelable(ARG_USER_INFO);
            pushExtras = getArguments().getString(ARG_PUSH_EXTRAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadWebView();
    }

    private void loadWebView() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME)
                .authority(URL_AUTHORITY)
                .appendPath(URL_PATH_APP)
                .appendQueryParameter(QUERY_APPLICATION, config.appId)
                .appendQueryParameter(QUERY_PLATFORM, QUERY_VALUE_ANDROID);

        //Append user email if present
        if (userInfo.userEmail != null) {
            builder.appendQueryParameter(QUERY_EMAIL, userInfo.userEmail);
        }

        //Append push token if present
        if (userInfo.pushTokenString != null) {
            builder.appendQueryParameter(QUERY_PUSH, userInfo.pushTokenString);
        }

        //If we have push extras, iterate through them and add
        if (pushExtras != null) {
            try {
                JSONObject dashJSONObject = new JSONObject(pushExtras);
                Iterator<String> keyIterator = dashJSONObject.keys();
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    String value = dashJSONObject.getString(key);
                    if (value != null) {
                        Log.d("PUSH_EXTRAS", "Adding key: " + key + " for value: " + value);
                        builder.appendQueryParameter(key, value);
                    }
                }
            } catch (JSONException e) {
                //No-op
            }
        }

        String fullURL = builder.build().toString();
        Log.d("DASHFragment", fullURL);
        webView.loadUrl(fullURL);
    }
}
