package com.sopnolikhi.booksmyfriend.Design.Adapters.Navigations.Invite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.Navigations.ContactModel;

public class ContactAdapter extends ListAdapter<ContactModel, ContactViewHolder> {

    private static final DiffUtil.ItemCallback<ContactModel> diffCallback = new DiffUtil.ItemCallback<ContactModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ContactModel oldItem, @NonNull ContactModel newItem) {
            return oldItem.getNumber().equals(newItem.getNumber());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactModel oldItem, @NonNull ContactModel newItem) {
            return oldItem.getNumber().equals(newItem.getNumber());
        }
    };

    public ContactAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setBindContactItem(getItem(position));
    }
}
