package io.dashapp.dashembedexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import io.dashapp.dashembed.Config;
import io.dashapp.dashembed.DASH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Config config = new Config("fcdallas", "hopscotch", "io.dashapp.dashembedexample");
        DASH.getInstance().startWithConfig(config);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDASHFragment();
            }
        });
    }

    public void pushDASHFragment() {
        Fragment fragment = DASH.getInstance().dashFragement();
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .replace(R.id.main_panel, fragment, "DASHFragment")
                .addToBackStack("DASHFragment")
                .commit();
        fragmentManager.executePendingTransactions();
    }
}
