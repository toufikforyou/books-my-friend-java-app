package com.sopnolikhi.booksmyfriend;

import static com.sopnolikhi.booksmyfriend.Design.Dialogs.Review.InAppReview.Review;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.sopnolikhi.booksmyfriend.Design.Dialogs.Exists.AlertDialogBox;
import com.sopnolikhi.booksmyfriend.Design.Dialogs.Exists.ClickDialog;
import com.sopnolikhi.booksmyfriend.Design.Dialogs.Update.InAppUpdate;
import com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Authentication.AuthUserActivity;
import com.sopnolikhi.booksmyfriend.Services.Includes.InAppSetting.InAppSetting;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;
import com.sopnolikhi.booksmyfriend.Services.Sessions.LoginSession;
import com.sopnolikhi.booksmyfriend.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ClickDialog {

    ActivityMainBinding binding;
    private LoginSession loginSession;

    private AlertDialogBox alertDialogBox;
    private NavController navController;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        if (loginSession == null) {
            loginSession = new LoginSession(getApplicationContext());
        }

        if (!loginSession.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), AuthUserActivity.class));
            finish();
        }

        setSupportActionBar(binding.applicationToolbarInclude.mApplicationToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.appNameText));

        // Drawable Navigation Show Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayoutMain, binding.applicationToolbarInclude.mApplicationToolbar, R.string.openNavigationText, R.string.closeNavigationText);
        binding.drawerLayoutMain.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_justify_icon);


        if (alertDialogBox == null) {
            alertDialogBox = new AlertDialogBox(this, this);
        }

        // TODO: Navigation Menu On click list ner;
        binding.navigation.setItemIconTintList(null); // TODO: This line Menu navigation icon color null code

        // navController = Navigation.findNavController(this, R.id.mainContainerView);


        // TODO: Fragment change with navigation controller
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


        NavigationUI.setupWithNavController(binding.navigation, navController);

        // TODO: Bottom Fragment Load In Navigation View Fragment View
        binding.bottomNavigation.setItemIconTintList(null); // TODO: This line bottom nav icon color null code

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        // Register options menu
        Menu menu = binding.navigation.getMenu();
        menu.findItem(R.id.app_nav_update).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.app_nav_privacy_policy).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.app_nav_terms_condition).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.app_nav_contact_us).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.app_nav_faq).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.app_nav_shire).setOnMenuItemClickListener(this::onOptionsItemSelected);


        // TODO: Another Fragment Open to bottom Navbar hide

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Check if the current destination is the fragment you want to discipline the button navigation for
            if (destination.getId() == R.id.app_nav_invite) {
                binding.bottomNavigation.animate().translationY(0).setDuration(500).start();
                binding.bottomNavigation.setVisibility(View.GONE);
            } else {
                binding.bottomNavigation.animate().translationY(0).setDuration(500).start();
                binding.bottomNavigation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

        // TODO:: LANGUAGE CHANGE
        UserSetting.UpdateApplicationLanguage(this, UserSetting.SETTING_LANGUAGE_USER_SET);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Find the current fragment in the NavHostFragment
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        // Delegate the result to the current fragment
        currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.app_nav_update) {
            closeDrawer();
            InAppUpdate.AppUpdate(this);
            return true;
        } else if (id == R.id.app_nav_privacy_policy) {
            closeDrawer();

            InAppSetting.InAppBrowser(this, "https://booksmyfriend.app/privacy-policy");
            return true;
        } else if (id == R.id.app_nav_terms_condition) {
            closeDrawer();

            InAppSetting.InAppBrowser(this, "https://booksmyfriend.app/terms-conditions");
            return true;
        } else if (id == R.id.app_nav_contact_us) {
            closeDrawer();

            InAppSetting.InAppBrowser(this, "https://booksmyfriend.app/contact-us");
            return true;
        } else if (id == R.id.app_nav_faq) {
            closeDrawer();
            InAppSetting.InAppBrowser(this, "https://booksmyfriend.app/faq");
            return true;
        } else if (id == R.id.app_nav_shire) {
            closeDrawer();

            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BOOKS MY FRIEND Application shire your friend");
            shareIntent.putExtra(Intent.EXTRA_REFERRER, "BOOKS MY FRIEND");
            shareIntent.putExtra(Intent.EXTRA_TITLE, "BMF - BOOKS MY FRIEND Application shire your friend");
            String shareMassage = "https://play.google.com/store/apps/details?id=com.sopnolikhi.booksmyfriend";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMassage);

            startActivity(Intent.createChooser(shareIntent, "Share Via - " + getString(R.string.appNameText)));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void closeDrawer() {
        if (binding.drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutMain.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutMain.closeDrawer(GravityCompat.START);
        } else {
            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.app_nav_home) {
                alertDialogBox.exitAlertDialogBox();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp() || navController.popBackStack();
    }

    @Override
    public void onYesClick() {
        super.onBackPressed();
    }

    @Override
    public void opinionOurApplication() {
        Review(this);
    }
}