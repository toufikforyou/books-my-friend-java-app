package com.sopnolikhi.booksmyfriend.Design.Adapters.NavHome;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.BottomNavigation.NavHome.BooksFragment;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    private final List<String> fragmentUrlList = new ArrayList<>();

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    // Add this method to get the current fragment


    public void addFragment(Fragment fragment, String title, String url) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        fragmentUrlList.add(url);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public String getUrl(int selectedTabPosition) {
        return fragmentUrlList.get(selectedTabPosition);
    }
}