package com.will_russell.food_flex_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import android.view.Gravity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.provider.MediaStore;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SubmissionActivity extends AppCompatActivity {

    static final int GET_FROM_GALLERY = 3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ArrayList<Bitmap> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void uploadPhoto(View v) {
        if (checkImageCount() == true) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        } else {
            View contextView = findViewById(R.id.camera_button);
            Snackbar.make(contextView, "You can't submit any more images", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View v) {
        if (checkImageCount() == true) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            View contextView = findViewById(R.id.camera_button);
            Snackbar.make(contextView, "You can't submit any more images", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean checkImageCount() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ImageLayout);
        if (layout.getChildCount() < 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                images.add(imageBitmap);
                insertImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images.add(imageBitmap);
            insertImage();
        }
    }

    private void insertImage() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ImageLayout);
        layout.removeAllViews();
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

    private MaterialCardView buildCardView(int index) {
        MaterialCardView cv = new MaterialCardView(this);
        cv.setId(index);
        MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(320, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        cv.setRadius(8f);
        cv.setElevation(10f);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) params;
        layoutParams.setMargins(14,5,5,14);
        cv.setLayoutParams(params);
        cv.requestLayout();
        return cv;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}