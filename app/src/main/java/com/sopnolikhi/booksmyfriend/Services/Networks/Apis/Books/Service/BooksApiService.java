package com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Books.Service;

import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.MyAllBooksResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.BookCreateRequestModel;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.Create.BookCreateResponseModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BooksApiService {
    @POST("/v1/create/book")
    @Multipart
    Call<BookCreateResponseModel> createBook(@Part MultipartBody.Part image, @Part("info") BookCreateRequestModel bookCreateRequestModel);

    @POST("/v1/myaccount/books/all")
    Call<MyAllBooksResponseModel> getAllMyBooks();
}
