package io.dashapp.dashembedexample;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.dashapp.dashembed.Config;
import io.dashapp.dashembed.DASH;
import io.dashapp.dashembed.DASHFragment;
import io.dashapp.dashembed.DASHFragmentEventListener;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder currentAlertDialog;

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
        DASHFragment fragment = DASH.getInstance().dashFragment();
        final Context context = this;
        fragment.setEventListener(new DASHFragmentEventListener() {
            @Override
            public void onReceivedError(Fragment dashFragment, int errorCode, String errorDescription) {
                if (currentAlertDialog != null) { return; }
                currentAlertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
                        .setTitle("Error")
                        .setMessage(errorDescription)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                currentAlertDialog = null;
                                dialogInterface.dismiss();
                            }
                        });
                currentAlertDialog.show();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .replace(R.id.main_panel, fragment, "DASHFragment")
                .commit();
        fragmentManager.executePendingTransactions();
    }
}
