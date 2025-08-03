package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.MyBooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sopnolikhi.booksmyfriend.databinding.FragmentCreateMyChapterBinding;

public class CreateMyChapterFragment extends Fragment {

    FragmentCreateMyChapterBinding myChapterBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myChapterBinding = FragmentCreateMyChapterBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_create_my_chapter, container, false);
        return myChapterBinding.getRoot();
    }
}