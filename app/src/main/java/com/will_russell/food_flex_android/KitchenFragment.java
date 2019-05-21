package com.will_russell.food_flex_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class KitchenFragment extends Fragment {

    public static KitchenFragment newInstance() {
        KitchenFragment fragment = new KitchenFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kitchen_fragment, container, false);
        return view;
        }

}
