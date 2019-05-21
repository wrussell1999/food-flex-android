package com.will_russell.food_flex_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SubmissionFragment extends Fragment {

    static final int GET_FROM_GALLERY = 3;
    static final int GET_IMAGE_CAPTURE = 1;
    ArrayList<Bitmap> images = new ArrayList<>();
    Submission submission;
    LinearLayout imageLayout;

    public static SubmissionFragment newInstance() {
        SubmissionFragment fragment = new SubmissionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submission_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            submit(view);
            Submission.submissionList.add(submission);
            //;
        });
        return view;
    }

    public void uploadPhoto(View v) {
        if (checkImageCount()) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        } else {
            View contextView = v.findViewById(R.id.camera_button);
            Snackbar.make(contextView, getResources().getString(R.string.error_images), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View v) {
        if (checkImageCount()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, GET_IMAGE_CAPTURE);
            }
        } else {
            View contextView = v.findViewById(R.id.camera_button);
            Snackbar.make(contextView, getResources().getString(R.string.error_images), Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean checkImageCount() {
        return imageLayout.getChildCount() < 3 && images.size() < 3;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                images.add(imageBitmap);
                insertImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == GET_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images.add(imageBitmap);
            insertImage();
        }
    }
    // Rebuilds ViewGroup from ArrayList
    private void insertImage() {
        imageLayout.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            ImageView iv = new ImageView(getContext());
            MaterialCardView cardView = buildCardView(i);
            iv.setId(i);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(images.get(i));
            cardView.addView(iv);
            imageLayout.addView(cardView);
        }
    }
    // Removes from ArrayList and rebuilds ViewGroup
    private void removeImage(int index) {
        images.remove(index);
        insertImage();
    }

    // Round corners
    private MaterialCardView buildCardView(int index) {
        MaterialCardView card = new MaterialCardView(getContext());
        card.setId(index);
        MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(320, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        card.setRadius(8f);
        card.setElevation(10f);
        params.setMargins(14,5,5,14);
        card.setLayoutParams(params);
        card.requestLayout();
        return card;
    }

    // Creates submission object
    private void submit(View view) {
        EditText titleView = view.findViewById(R.id.name_field);
        EditText descriptionView = view.findViewById(R.id.description_field);
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();
        submission = new Submission(title, description, images);
    }
}
