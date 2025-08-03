package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.Forgotten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.databinding.FragmentAccountFinderBinding;

public class AccountFinderFragment extends Fragment {
    FragmentAccountFinderBinding finderBinding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        finderBinding = FragmentAccountFinderBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_account_finder, container, false);
        return finderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        finderBinding.nextButton.setOnClickListener(v -> navController.navigate(R.id.action_accountFinderFragment_to_accountsListFragment));

        finderBinding.backButton.setOnClickListener(v -> navController.navigateUp());
    }
}