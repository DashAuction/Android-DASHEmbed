package io.dashapp.dashembed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
    private static final String BASE_URL = "https://web.dashapp.io/auctions/";

    private Config config;
    private WebView webView;

    public DASHFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param config The config to use for initializing the fragment.
     * @return A new instance of fragment DASHFragment.
     */
    public static DASHFragment newInstance(Config config) {
        DASHFragment fragment = new DASHFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONFIG, config);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            config = getArguments().getParcelable(ARG_CONFIG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        String fullURL = BASE_URL + config.teamIdentifier;
        webView.loadUrl(fullURL);
    }
}
