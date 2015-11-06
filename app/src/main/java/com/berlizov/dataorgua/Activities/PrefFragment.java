package com.berlizov.dataorgua.Activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.berlizov.dataorgua.R;

/**
 * Created by 350z6_000 on 05.11.2015.
 */
public class PrefFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
