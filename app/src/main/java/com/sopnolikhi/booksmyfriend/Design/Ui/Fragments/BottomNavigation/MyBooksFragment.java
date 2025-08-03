package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.BottomNavigation;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sopnolikhi.booksmyfriend.Design.Adapters.MyBooks.AllBooks.BookClickInterface;
import com.sopnolikhi.booksmyfriend.Design.Adapters.MyBooks.AllBooks.MyAllBooksAdapter;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Networks.Internet.Internet;
import com.sopnolikhi.booksmyfriend.ViewModel.MyBooks.AllBooks.MyAllBooksViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentMyBooksBinding;

public class MyBooksFragment extends Fragment implements BookClickInterface {

    FragmentMyBooksBinding myBooksBinding;
    private NavController navController;
    private MyAllBooksViewModel myAllBooksViewModel;
    private MyAllBooksAdapter myAllBooksAdapter;

    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myBooksBinding = FragmentMyBooksBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_my_books, container, false);
        return myBooksBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        // My All Books view model
        if (myAllBooksViewModel == null) {
            myAllBooksViewModel = new ViewModelProvider(requireActivity()).get(MyAllBooksViewModel.class);
        }

        // My Adapter
        if (myAllBooksAdapter == null) {
            myAllBooksAdapter = new MyAllBooksAdapter(this);
        }

        // recycler view
        myBooksBinding.myBooksListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myBooksBinding.myBooksListRecyclerView.setHasFixedSize(true);

        myAllBooksViewModel.getMyAllBooksLiveData().observe(getViewLifecycleOwner(), myAllBooksResData -> {
            myBooksBinding.swipeRefreshMyBooks.setRefreshing(false);
            myBooksBinding.progressBar.setVisibility(View.GONE);
            myBooksBinding.errorSmgLayout.setVisibility(View.GONE);

            if (myAllBooksResData instanceof ApiResponseModel.Loading) {
                myBooksBinding.progressBar.setVisibility(View.VISIBLE);
            } else if (myAllBooksResData instanceof ApiResponseModel.SuccessCode) {
                if (myAllBooksResData.getSuccessCode() == 1000) {
                    myBooksBinding.myBooksListRecyclerView.setVisibility(View.VISIBLE);
                    myAllBooksAdapter.submitList(myAllBooksResData.getData());
                    myBooksBinding.myBooksListRecyclerView.setAdapter(myAllBooksAdapter);
                }
            } else if (myAllBooksResData instanceof ApiResponseModel.ErrorCode) {
                if (myAllBooksResData.getErrorCode() == 1054) {
                    myBooksBinding.errorSmgLayout.setVisibility(View.VISIBLE);
                    myBooksBinding.errorSmg.setText(myAllBooksResData.getMessage());
                } else {
                    Toast.makeText(requireContext(), myAllBooksResData.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        getFetchBooks();

        myBooksBinding.swipeRefreshMyBooks.setOnRefreshListener(this::getFetchBooks);
    }

    private void getFetchBooks() {
        if (Internet.isConnected(requireContext())) {
            myAllBooksViewModel.getUpdateBooks();
        } else {
            Toast.makeText(requireContext(), "Internet Connection error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        menu.findItem(R.id.create_book).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_book) {
            bundle = new Bundle();
            bundle.putInt("bid", 0);
            navController.navigate(R.id.action_nav_my_books_to_createBookActivity, bundle);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowBookDetails(int bid, String bName) {
        bundle = new Bundle();
        bundle.putInt("bid", bid);
        bundle.putString("bname", bName);
        navController.navigate(R.id.action_nav_my_books_to_createBookActivity, bundle);
    }
}