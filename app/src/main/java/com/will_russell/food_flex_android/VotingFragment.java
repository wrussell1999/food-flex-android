package com.will_russell.food_flex_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class VotingFragment extends Fragment {

    public static VotingFragment newInstance() {
        VotingFragment fragment = new VotingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submission_fragment, container, false);
        return view;
    }
}

