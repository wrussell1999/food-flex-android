package com.will_russell.food_flex_android;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.util.TypedValue;
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

public class SubmissionActivity extends AppCompatActivity {

    static final int GET_FROM_GALLERY = 3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

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
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    public void takePhoto(View v) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ImageLayout);
        if (layout.getChildCount() <= 3) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            View contextView = findViewById(R.id.camera_button);
            Snackbar.make(contextView, "You can't submit any more images", Snackbar.LENGTH_SHORT).show();
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
                //mImageView.setImageBitmap(imageBitmap);
                insertImage(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //ImageView.setImageBitmap(imageBitmap);
            insertImage(imageBitmap);
        }
    }

    private void insertImage(Bitmap data) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ImageLayout);
        layout.removeAllViews();
        ImageView iv = new ImageView(this);
        MaterialCardView cardView = buildCardView();
        iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageBitmap(data);
        cardView.addView(iv);
        layout.addView(cardView);
    }

    private MaterialCardView buildCardView() {
        MaterialCardView cv = new MaterialCardView(this);
        MaterialCardView.LayoutParams params = new MaterialCardView.LayoutParams(360, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        cv.setRadius(8f);
        cv.setElevation(10f);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) params;
        layoutParams.setMargins(5,5,5,5);
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
