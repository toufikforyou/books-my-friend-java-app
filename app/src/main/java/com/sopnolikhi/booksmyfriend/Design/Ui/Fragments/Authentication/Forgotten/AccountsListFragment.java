package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.Forgotten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.sopnolikhi.booksmyfriend.databinding.FragmentAccountsListBinding;

public class AccountsListFragment extends Fragment {
    FragmentAccountsListBinding accountsListBinding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountsListBinding = FragmentAccountsListBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_accounts_list, container, false);
        return accountsListBinding.getRoot();
    }
}