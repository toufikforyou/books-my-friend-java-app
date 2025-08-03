package com.sopnolikhi.booksmyfriend.Design.Adapters.MyBooks.AllBooks;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.Result.MyAllBooksResData;
import com.sopnolikhi.booksmyfriend.databinding.SingleMyBookListLayoutBinding;

import java.util.Objects;

public class MyAllBooksViewHolder extends RecyclerView.ViewHolder {
    SingleMyBookListLayoutBinding myBookListLayoutBinding;

    public MyAllBooksViewHolder(@NonNull View itemView) {
        super(itemView);
        myBookListLayoutBinding = SingleMyBookListLayoutBinding.bind(itemView);
    }


    public void setBindMyBooksItem(MyAllBooksResData bindMyBooksItem, BookClickInterface bookClickInterface) {
        myBookListLayoutBinding.getRoot().setOnClickListener(v -> {
            bookClickInterface.onShowBookDetails(Integer.parseInt(bindMyBooksItem.getBid()), bindMyBooksItem.getBname());
        });
        myBookListLayoutBinding.bookName.setText(bindMyBooksItem.getBname());
        if (!bindMyBooksItem.getDescription().isEmpty()) {
            myBookListLayoutBinding.shortInfoBook.setText(bindMyBooksItem.getDescription());
        } else {
            myBookListLayoutBinding.shortInfoBook.setText(R.string.notSetText);
        }

        if (Objects.equals(bindMyBooksItem.getAuthor(), "0")) {
            myBookListLayoutBinding.authorName.setText(R.string.notSetText);
        } else {
            myBookListLayoutBinding.authorName.setText(bindMyBooksItem.getAuthor());
        }

        if (!bindMyBooksItem.getCover().isEmpty()) {
            String url = "https://files.sopnolikhi.com/booksmyfriend/unpublish/" + bindMyBooksItem.getUid() + "/" + bindMyBooksItem.getBid() + "/" + bindMyBooksItem.getCover();
            Glide.with(itemView.getContext()).load(url).error(R.drawable.ic_error_1023).placeholder(R.drawable.loading_img).into(myBookListLayoutBinding.bookCover);
        }


    }
}
