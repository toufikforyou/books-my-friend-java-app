package com.sopnolikhi.booksmyfriend.ViewModel.MyBooks.AllBooks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.Result.MyAllBooksResData;
import com.sopnolikhi.booksmyfriend.Services.Repositories.MyBooks.AllBooks.MyAllBooksRepository;

import java.util.List;

public class MyAllBooksViewModel extends AndroidViewModel {
    private final MyAllBooksRepository myAllBooksRepository;

    public MyAllBooksViewModel(@NonNull Application application) {
        super(application);
        this.myAllBooksRepository = new MyAllBooksRepository(application);
    }

    public LiveData<ApiResponseModel<List<MyAllBooksResData>>> getMyAllBooksLiveData() {
        return myAllBooksRepository.getMyAllBooksLiveData();
    }

    public void getUpdateBooks() {
        myAllBooksRepository.requestMyBooks();
    }
}
