package com.sopnolikhi.booksmyfriend.Design.Adapters.MyBooks.AllBooks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.MyBooks.All.Result.MyAllBooksResData;

public class MyAllBooksAdapter extends ListAdapter<MyAllBooksResData, MyAllBooksViewHolder> {
    private final BookClickInterface bookClickInterface;

    private static final DiffUtil.ItemCallback<MyAllBooksResData> MY_ALL_BOOKS_RES_DATA_ITEM_CALLBACK = new DiffUtil.ItemCallback<MyAllBooksResData>() {
        @Override
        public boolean areItemsTheSame(@NonNull MyAllBooksResData oldItem, @NonNull MyAllBooksResData newItem) {
            return oldItem.getBid().equals(newItem.getBid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MyAllBooksResData oldItem, @NonNull MyAllBooksResData newItem) {
            return oldItem.getBid().equals(newItem.getBid());
        }
    };

    public MyAllBooksAdapter(BookClickInterface bookClickInterface) {
        super(MY_ALL_BOOKS_RES_DATA_ITEM_CALLBACK);
        this.bookClickInterface = bookClickInterface;
    }


    @NonNull
    @Override
    public MyAllBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAllBooksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_my_book_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAllBooksViewHolder holder, int position) {
        holder.setBindMyBooksItem(getItem(position), bookClickInterface);
    }
}
