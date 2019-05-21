package com.will_russell.food_flex_android;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment;
            Resources resources = getResources();
            switch (item.getItemId()) {
                case R.id.kitchen_menu_button:
                    selectedFragment = KitchenFragment.newInstance();
                    toolbar.setTitle(resources.getString(R.string.kitchen_title));
                    break;
                case R.id.submit_menu_button:
                    selectedFragment = SubmissionFragment.newInstance();
                    toolbar.setTitle(resources.getString(R.string.submission_title));
                    break;
                case R.id.vote_menu_buttton:
                    selectedFragment = VotingFragment.newInstance();
                    toolbar.setTitle(resources.getString(R.string.voting_title));
                    break;
                case R.id.results_menu_button:
                    selectedFragment = ResultsFragment.newInstance();
                    toolbar.setTitle(resources.getString(R.string.results_title));
                    break;
                default:
                    selectedFragment = KitchenFragment.newInstance();
                    toolbar.setTitle(resources.getString(R.string.kitchen_title));
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, KitchenFragment.newInstance());
        transaction.commit();

    }

    public void openSubmission(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, IndividualFragment.newInstance(position));
        transaction.commit();
    }

    public void submissionReminder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Submissions")
                .setSmallIcon(R.drawable.time)
                .setContentTitle("1 hour left for submissions")
                .setContentText("There's still time to submit today's flex!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
