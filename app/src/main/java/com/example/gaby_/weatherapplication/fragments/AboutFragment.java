package com.example.gaby_.weatherapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaby_.weatherapplication.BuildConfig;
import com.example.gaby_.weatherapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {
    private TextView versionTextView;
    private TextView sdkTextView;

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_about, container, false);
        versionTextView = (TextView) mainView.findViewById(R.id.version_text);
        sdkTextView = (TextView) mainView.findViewById(R.id.sdk_text);
        versionTextView.setText(BuildConfig.VERSION_NAME);
        sdkTextView.setText(getResources().getString(R.string.min_sdk));
        return mainView;
    }
}
