package com.will_russell.food_flex_android;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IndividualFragment extends Fragment {

    static Submission submission;

    public static IndividualFragment newInstance(int position) {
        IndividualFragment fragment = new IndividualFragment();
        submission = Submission.submissionList.get(position);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.individual_fragment, container, false);
        return view;
    }
}
