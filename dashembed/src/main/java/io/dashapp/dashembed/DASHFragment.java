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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DASHFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DASHFragment extends Fragment {
    private static final String ARG_CONFIG = "config";
    private static final String ARG_USER_INFO = "userInfo";
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
    public static DASHFragment newInstance(Config config, UserInfo userInfo) {
        DASHFragment fragment = new DASHFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONFIG, config);
        args.putParcelable(ARG_USER_INFO, userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            config = getArguments().getParcelable(ARG_CONFIG);
            userInfo = getArguments().getParcelable(ARG_USER_INFO);
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
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME)
                .authority(URL_AUTHORITY)
                .appendPath(URL_PATH_APP)
                .appendQueryParameter(QUERY_APPLICATION, config.appId)
                .appendQueryParameter(QUERY_PLATFORM, QUERY_VALUE_ANDROID);

        if (userInfo.userEmail != null) {
            builder.appendQueryParameter(QUERY_EMAIL, userInfo.userEmail);
        }

        if (userInfo.pushTokenString != null) {
            builder.appendQueryParameter(QUERY_PUSH, userInfo.pushTokenString);
        }
        String fullURL = builder.build().toString();
        Log.d("DASHFragment", fullURL);
        webView.loadUrl(fullURL);
    }
}
