package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.BottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.sopnolikhi.booksmyfriend.Design.Adapters.NavHome.PageAdapter;
import com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Authentication.AuthUserActivity;
import com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.BottomNavigation.NavHome.BooksFragment;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Sessions.LoginSession;
import com.sopnolikhi.booksmyfriend.databinding.FragmentNavHomeBinding;

public class NavHomeFragment extends Fragment {

    FragmentNavHomeBinding navHomeBinding;
    LoginSession loginSession;
    PageAdapter adapter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        navHomeBinding = FragmentNavHomeBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_nav_home, container, false);
        return navHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (loginSession == null) {
            loginSession = new LoginSession(requireContext());
        }

        adapter = new PageAdapter(getChildFragmentManager());
        adapter.addFragment(new BooksFragment(), getResources().getString(R.string.allBooksText), "https://example.com/all");
        adapter.addFragment(new BooksFragment(), getResources().getString(R.string.favoriteBooksText), "https://example.com/favorite");
        adapter.addFragment(new BooksFragment(), getResources().getString(R.string.popularBooksText), "https://example.com/popular");

        navHomeBinding.viewPager.setAdapter(adapter);
        navHomeBinding.tabLayout.setupWithViewPager(navHomeBinding.viewPager);

        navHomeBinding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapter.getUrl(tab.getPosition());
                // Toast.makeText(requireContext(), "" + adapter.getUrl(tab.getPosition()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.logout).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            loginSession.logout();
            startActivity(new Intent(requireActivity(), AuthUserActivity.class));
            requireActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }
}