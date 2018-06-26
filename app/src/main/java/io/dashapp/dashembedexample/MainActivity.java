package io.dashapp.dashembedexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.dashapp.dashembed.Config;
import io.dashapp.dashembed.DASH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the DASH instance
        Config config = new Config("fcdallas", "hopscotch", "io.dashapp.dashembedexample");
        DASH.getInstance().startWithConfig(config);

        //Set an email
        DASH.getInstance().setUserEmail("ryan@dashapp.io");

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDASHFragment();
            }
        });
    }

    public void pushDASHFragment() {
        Fragment fragment = DASH.getInstance().dashFragment();
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .replace(R.id.main_panel, fragment, "DASHFragment")
                .addToBackStack("DASHFragment")
                .commit();
        fragmentManager.executePendingTransactions();
    }
}
