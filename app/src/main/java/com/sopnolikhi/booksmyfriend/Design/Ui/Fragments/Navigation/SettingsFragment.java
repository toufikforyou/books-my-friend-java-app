package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceFragmentCompat;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getPreferenceManager().getSharedPreferences()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        onUpdateListPreference(key);
    }

    private void onUpdateListPreference(String key) {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        if (sharedPreferences != null) {
            if (UserSetting.NIGHT_MODE.equals(key)) {
                UserSetting.NightModeApply(sharedPreferences.getString(UserSetting.NIGHT_MODE, "system"));

            } else if (UserSetting.SETTING_LANGUAGE.equals(key)) {
                String defaultLanguage = sharedPreferences.getString("countryCode", "").equals("BD") ? "bn" : "en";
                UserSetting.UpdateApplicationLanguage(requireContext(), sharedPreferences.getString(UserSetting.SETTING_LANGUAGE, defaultLanguage).equals("any") ? defaultLanguage : sharedPreferences.getString(UserSetting.SETTING_LANGUAGE, defaultLanguage));
                showRestartDialog();
            }
        }
    }

    private void showRestartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Restart Required");
        builder.setMessage("To apply the new language, please restart the app.");
        builder.setPositiveButton("Restart", (dialog, which) -> {
            // Restart the app
            Intent restartIntent = requireActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(requireActivity().getBaseContext().getPackageName());
            assert restartIntent != null;
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);
            requireActivity().finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onResume() {
        Objects.requireNonNull(getPreferenceManager().getSharedPreferences()).registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Objects.requireNonNull(getPreferenceManager().getSharedPreferences()).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}