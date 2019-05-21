package com.will_russell.food_flex_android;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

public class VotingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final String SERVER_URL = "";//getResources().getString(R.string.server_url);
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static SubmissionAdapter adapter;

    public static VotingFragment newInstance() {
        VotingFragment fragment = new VotingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submission_fragment, container, false);

        Context context = getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        adapter = new SubmissionAdapter(getContext());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(() -> {
            loadRecyclerViewData();
        });

        return view;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        loadRecyclerViewData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void loadRecyclerViewData() {
        RequestQueue queue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, SERVER_URL, null, response -> {

        }, error -> Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show());
        queue.add(jsonObjectRequest);
        adapter.notifyDataSetChanged();
    }
}

