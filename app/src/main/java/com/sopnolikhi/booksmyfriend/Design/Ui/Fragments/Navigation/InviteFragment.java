package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Navigation;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sopnolikhi.booksmyfriend.Design.Adapters.Navigations.Invite.ContactAdapter;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Permissions.Contact;
import com.sopnolikhi.booksmyfriend.Services.Permissions.PermissionCallback;
import com.sopnolikhi.booksmyfriend.ViewModel.Navigation.Invite.ContactViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentInviteBinding;

public class InviteFragment extends Fragment {

    FragmentInviteBinding inviteBinding;
    private ContactAdapter contactAdapter;
    private ContactViewModel contactViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inviteBinding = FragmentInviteBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_invite, container, false);
        return inviteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (contactAdapter == null) {
            contactAdapter = new ContactAdapter();
        }

        setContactViewModelRecycleView();

        inviteBinding.contactInvite.setLayoutManager(new LinearLayoutManager(requireActivity()));
        inviteBinding.contactInvite.setHasFixedSize(true);

        inviteBinding.retryBtn.setOnClickListener(v -> setContactViewModelRecycleView());

        inviteBinding.swipeRefreshMyContact.setOnRefreshListener(this::setContactViewModelRecycleView);

    }

    private void setContactViewModelRecycleView() {
        if (Contact.hasContactPermissions(requireActivity())) {
            inviteBinding.permissionError.setVisibility(View.GONE);
            setHasOptionsMenu(true);
            if (contactViewModel == null) {
                contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
            }
            contactViewModel.getContactList().observe(requireActivity(), inviteContactLists -> {
                inviteBinding.swipeRefreshMyContact.setRefreshing(false);
                contactAdapter.submitList(inviteContactLists);
                inviteBinding.contactInvite.setAdapter(contactAdapter);
            });
        } else {
            Contact.requestContactPermissions(requireActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Contact.onRequestPermissionsResult(requestCode, grantResults, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                setContactViewModelRecycleView();
            }

            @Override
            public void onPermissionDenied() {
                Contact.handlePermissionResult(requireActivity(), grantResults, new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        // Assuming setContactViewModelRecycleView() handles RecyclerView updates
                        setContactViewModelRecycleView();
                        // Add any additional logic related to RecyclerView refresh if needed
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });

                permRequestLayout();
            }
        });
    }

    private void permRequestLayout() {
        inviteBinding.permissionError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        menu.findItem(R.id.search).setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        assert searchView != null;
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.searchHereText));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update the ViewModel with the new search query.
                contactViewModel.searchContacts(newText.trim());
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}