package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.MyBooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.databinding.FragmentChapterViewBinding;

public class ChapterViewFragment extends Fragment {
    FragmentChapterViewBinding chapterViewBinding;
    private NavController navController;
    private int bookId;
    private String bName;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chapterViewBinding = FragmentChapterViewBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_chapter_view, container, false);
        return chapterViewBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        // Retrieve the bundle sent from the navigation action
        bundle = getArguments();
        if (bundle != null) {
            bookId = bundle.getInt("bid", 0); // Provide a default value if needed
            bName = bundle.getString("bname", "");
            if (bookId == 0) {
                bundle.putInt("bid", 1);
                navController.navigate(R.id.action_chapterViewFragment_to_createBooksFragment);
                return;
            }
            if (bookId == 1) {
                requireActivity().finish();
                return;
            }
        }

        // On Change title;
        onChangeTitle(bName);


        Toast.makeText(requireContext(), "" + bookId, Toast.LENGTH_SHORT).show();
    }

    void onChangeTitle(String title) {
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        menu.findItem(R.id.create_book_chapter).setVisible(true);
        menu.findItem(R.id.update_book_info).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_book_chapter) {
            bundle = new Bundle();
            bundle.putInt("bid", bookId);
            navController.navigate(R.id.action_chapterViewFragment_to_createMyChapterFragment, bundle);
        }
        return super.onOptionsItemSelected(item);
    }
}