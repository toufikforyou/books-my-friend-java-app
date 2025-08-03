package com.sopnolikhi.booksmyfriend.ViewModel.MyBooks.Create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.Result.CreateBookResData;
import com.sopnolikhi.booksmyfriend.Services.Repositories.MyBooks.Create.CreateBookRepository;

import java.io.File;

public class CreateBookViewModel extends AndroidViewModel {
    private final CreateBookRepository createBookRepository;

    public CreateBookViewModel(@NonNull Application application) {
        super(application);
        this.createBookRepository = new CreateBookRepository(application);
    }

    public LiveData<ApiResponseModel<CreateBookResData>> getCreateBookLiveData() {
        return createBookRepository.getCreateBookLiveData();
    }

    public void requestCreateBook(String name, String info, File cover) {
        createBookRepository.createBookRequest(name, info, cover);
    }

    public void clearCreateBookData() {
        createBookRepository.clearCreateBookData();
    }
}
