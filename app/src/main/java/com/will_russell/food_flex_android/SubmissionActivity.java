package com.will_russell.food_flex_android;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class SubmissionActivity extends AppCompatActivity {

    static final int GET_FROM_GALLERY = 3;
    static final int GET_IMAGE_CAPTURE = 1;
    ArrayList<Bitmap> images = new ArrayList<>();
    Submission submission;
    LinearLayout imageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        
        imageLayout = findViewById(R.id.ImageLayout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            submit();
            Submission.submissionList.add(submission);
            finish();
        });
    }

    public void uploadPhoto(View v) {
        if (checkImageCount()) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        } else {
            View contextView = findViewById(R.id.camera_button);
            Snackbar.make(contextView, getResources().getString(R.string.error_images), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View v) {
        if (checkImageCount()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, GET_IMAGE_CAPTURE);
            }
        } else {
            View contextView = findViewById(R.id.camera_button);
            Snackbar.make(contextView, getResources().getString(R.string.error_images), Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean checkImageCount() {
        return imageLayout < 3 && iamges.size() < 3;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
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

    // Removes from ArrayList and rebuilds ViewGroup
    private void removeImage(int index) {
        images.remove(index);
        insertImage();
    }

    // Rebuilds ViewGroup from ArrayList
    private void insertImage() {
        imageLayout.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            ImageView iv = new ImageView(this);
            MaterialCardView cardView = buildCardView(i);
            iv.setId(i);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(images.get(i));
            cardView.addView(iv);
            layout.addView(cardView);
        }
    }

    // Round corners
    private MaterialCardView buildCardView(int index) {
        MaterialCardView card = new MaterialCardView(this);
        card.setId(index);
        MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(320, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        card.setRadius(8f);
        card.setElevation(10f);
        params.setMargins(14,5,5,14);
        card.setLayoutParams(params);
        card.requestLayout();
        return card;
    }

    // Creates submission object
    private void submit() {
        EditText titleView = findViewById(R.id.name_field);
        EditText descriptionView = findViewById(R.id.description_field);
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();
        submission = new Submission(title, description, images);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}