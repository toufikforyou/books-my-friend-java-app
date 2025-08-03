package com.sopnolikhi.booksmyfriend.ViewModel.Navigation.Invite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Navigations.ContactModel;
import com.sopnolikhi.booksmyfriend.Services.Repositories.Navigations.Invite.InviteRepository;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final InviteRepository inviteRepository;
    private LiveData<List<ContactModel>> contactList;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        inviteRepository = new InviteRepository(application);
        contactList = inviteRepository.getContactList();
    }

    public LiveData<List<ContactModel>> getContactList() {
        return contactList;
    }

    public void searchContacts(String query) {
        inviteRepository.searchContacts(query);
    }
}
