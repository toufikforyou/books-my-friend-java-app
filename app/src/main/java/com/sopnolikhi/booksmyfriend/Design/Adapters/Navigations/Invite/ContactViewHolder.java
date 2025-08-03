package com.sopnolikhi.booksmyfriend.Design.Adapters.Navigations.Invite;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Models.Navigations.ContactModel;
import com.sopnolikhi.booksmyfriend.databinding.SingleContactItemBinding;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    SingleContactItemBinding itemBinding;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        itemBinding = SingleContactItemBinding.bind(itemView);
    }

    @SuppressLint("SetTextI18n")
    public void setBindContactItem(ContactModel contactItem) {
        itemBinding.contactName.setText(contactItem.getName());
        itemBinding.contactNumber.setText(contactItem.getNumber());

        if (contactItem.getProfile() != null) {
            Glide.with(itemBinding.getRoot().getContext()).load(contactItem.getProfile()).error(R.drawable.ic_link_icon).into(itemBinding.contactIcon);
        } else {
            itemBinding.contactIcon.setImageResource(R.drawable.ic_avater_icon);
        }
    }
}
