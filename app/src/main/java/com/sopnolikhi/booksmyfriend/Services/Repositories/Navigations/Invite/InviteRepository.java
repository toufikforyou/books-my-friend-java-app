package com.sopnolikhi.booksmyfriend.Services.Repositories.Navigations.Invite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import com.sopnolikhi.booksmyfriend.Services.Models.Navigations.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class InviteRepository {
    private final ArrayList<ContactModel> arrayList = new ArrayList<>();
    private final Context mContext;
    private MutableLiveData<List<ContactModel>> originalData;

    public InviteRepository(Context context) {
        this.mContext = context;
    }


    public MutableLiveData<List<ContactModel>> getContactList() {
        if (originalData == null) {
            originalData = new MutableLiveData<>();
            fetchContactList();
        }
        return originalData;
    }

    public void fetchContactList() {

        @SuppressLint("Recycle") Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        while (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") String _avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
            @SuppressLint("Range") String _contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String _contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if (_contactNumber.matches("^(?:\\+88|88)?(01[3-9]\\d{8})$")) {
                arrayList.add(new ContactModel(_avatar, _contactName, _contactNumber));
            }

        }
        originalData.postValue(arrayList);

        assert cursor != null;
        cursor.close();
    }

    public void searchContacts(String query) {
        if (query.isEmpty()) {
            // If the query is empty, fetch the entire contact list
            originalData.postValue(arrayList);

        } else {
            // If there's a search query, filter the list based on the query
            List<ContactModel> filteredList = new ArrayList<>();

            for (ContactModel contact : arrayList) {
                if (contact.getName().toLowerCase().contains(query.toLowerCase()) || contact.getNumber().contains(query)) {
                    filteredList.add(contact);
                }
            }

            // Clear the existing list before adding the filtered items
            originalData.postValue(filteredList);
        }
    }
}
