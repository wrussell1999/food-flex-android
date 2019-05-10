package com.will_russell.food_flex_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.ViewHolder> {

    Context context;

    public SubmissionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.submission_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Submission.submissionList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = Submission.submissionList.get(position);
        holder.titleView.setText(holder.mItem.getTitle());
        holder.imageView.setImageBitmap(holder.mItem.getImage(0));
        holder.authorView.setText(holder.mItem.getAuthor());

        holder.mView.setClickable(true);
        holder.mView.setOnClickListener(v -> ((VotingActivity) holder.mView.getContext()).openSubmission(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final ImageView imageView;
        public final TextView authorView;

        public Submission mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = view.findViewById(R.id.titleView);
            imageView = view.findViewById(R.id.imageView);
            authorView = view.findViewById(R.id.authorView);
        }
    }
}
