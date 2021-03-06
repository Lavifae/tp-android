package ru.lavifae.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mStrings;
    private static final String STRINGS_KEY = "savedStrings";
    private static final String CURRENT_FRAGMENT_KEY = "savedCurrentFragmentName";
    private String mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // restore after destruction
        if (savedInstanceState == null) {
            mStrings = new ArrayList<>();
            fillList(mStrings);
        } else {
            mStrings = savedInstanceState.getStringArrayList(STRINGS_KEY);
            mCurrentFragment = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
        }

        if (mCurrentFragment == null || mCurrentFragment.equals("list")) {
            ListFragment listFragment;

            // troubleshooting for helly twicecalling oncreate
            if (getSupportFragmentManager().findFragmentByTag("list") == null) {
                listFragment = ListFragment.newInstance(mStrings);
            }
            else {
                listFragment =  (ListFragment) getSupportFragmentManager().findFragmentByTag("list");
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_frame, Objects.requireNonNull(listFragment), "list")
                    .commit();
        }
    }


    void fillList(List<String> toFill) {
        for (int i = 1; i <= 100; i++) {
            toFill.add(i + "");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(STRINGS_KEY, mStrings);
        outState.putString(CURRENT_FRAGMENT_KEY, mCurrentFragment);
    }


    @Override
    protected void onPause() {
        // check which fragment were the last hero
        if (Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("list")).isVisible())
            mCurrentFragment = "list";
        else {
            mCurrentFragment = "number";
        }
        super.onPause();
    }
}
