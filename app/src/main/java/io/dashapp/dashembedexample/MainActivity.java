package io.dashapp.dashembedexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.dashapp.dashembed.Config;
import io.dashapp.dashembed.DASH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the DASH instance
        Config config = new Config("55e1bb99a1a135543f692bad");
        DASH.getInstance().startWithConfig(this, config);

        //Set an email
        DASH.getInstance().setUserEmail("ryan@dashapp.io");

        //** See ExampleInstanceIdService for push handling **
        //Check for push extras on the intent
        Intent intent = getIntent();
        handleIntent(intent);

        embedDASHFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (DASH.getInstance().canHandlePushIntentExtras(extras)) {
                DASH.getInstance().setPushIntentExtras(extras);
            }
        }
    }

    private void embedDASHFragment() {
        Fragment fragment = DASH.getInstance().dashFragment();
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .replace(R.id.main_panel, fragment, "DASHFragment")
                .commit();
        fragmentManager.executePendingTransactions();
    }
}
