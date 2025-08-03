package com.sopnolikhi.booksmyfriend.Design.Ui.Activities.MyBooks;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.databinding.ActivityCreateMyBookBinding;

import java.util.Objects;

public class CreateBookActivity extends AppCompatActivity {

    ActivityCreateMyBookBinding createMyBookBinding;

    NavController navController;
    private NavHostFragment navHostFragment;
    private Fragment currentFragment;
    private Bundle receivedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMyBookBinding = ActivityCreateMyBookBinding.inflate(getLayoutInflater());
        // setContentView(R.layout.activity_create_my_books);
        setContentView(createMyBookBinding.getRoot());

        setSupportActionBar(createMyBookBinding.applicationToolbarInclude.mApplicationToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);


        // TODO: Fragment change with navigation controller
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.myBooksContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        // Find the current fragment in the NavHostFragment
        currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);


        // TODO: Bundle receivedBundle and
        receivedBundle = getIntent().getExtras();

        if (receivedBundle != null) {
            currentFragment.setArguments(receivedBundle);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Delegate the result to the current fragment
        currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!onSupportNavigateUp()) {
                onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp() || navController.popBackStack();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}