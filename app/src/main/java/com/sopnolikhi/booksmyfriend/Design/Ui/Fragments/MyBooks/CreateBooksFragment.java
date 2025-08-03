package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.MyBooks;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Bitmap.BitmapUtils;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Permissions.PermissionCallback;
import com.sopnolikhi.booksmyfriend.Services.Permissions.Storage;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Common.ValidationViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.MyBooks.AllBooks.MyAllBooksViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.MyBooks.Create.CreateBookViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentCreateBooksBinding;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CreateBooksFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST_CODE = KeyValue.PICK_IMAGE_CODE;
    FragmentCreateBooksBinding createBooksBinding;
    private ValidationViewModel validationViewModel;
    private CreateBookViewModel createBookViewModel;
    private MyAllBooksViewModel myAllBooksViewModel;
    private NavController navController;
    private String coverPath;
    private String name, info;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createBooksBinding = FragmentCreateBooksBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_create_books, container, false);
        return createBooksBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (validationViewModel == null) {
            validationViewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        }

        if (createBookViewModel == null) {
            createBookViewModel = new ViewModelProvider(requireActivity()).get(CreateBookViewModel.class);
        }

        // Set the new title
        onChangeTitle(getString(R.string.createYourBookText));

        // TODO:: Observe the ViewModel user create a new book response method
        createBookViewModel.getCreateBookLiveData().observe(getViewLifecycleOwner(), createBookResData -> {
            createBooksBinding.createBookBtn.setLoading(false);
            if (createBookResData instanceof ApiResponseModel.Loading) {
                createBooksBinding.createBookBtn.setLoading(true);
            } else if (createBookResData instanceof ApiResponseModel.SuccessCode) {
                if (createBookResData.getSuccessCode() == 1001) {
                    bundle = new Bundle();
                    bundle.putInt("bid", Integer.parseInt(createBookResData.getData().getBid()));
                    bundle.putString("bname", createBookResData.getData().getBName());
                    createBookViewModel.clearCreateBookData();
                    navController.navigate(R.id.action_createBooksFragment_to_chapterViewFragment, bundle);
                }
            } else if (createBookResData instanceof ApiResponseModel.ErrorCode) {
                Log.d("ASASAS", createBookResData.getMessage());
                Toast.makeText(requireContext(), createBookResData.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Log.d("ASASASA", coverPath);
            }
        });

        createBooksBinding.createBookBtn.setText(getString(R.string.createBookText));

        createBooksBinding.bookCoverUploadButton.setOnClickListener(v -> imageUploadSelect());
        createBooksBinding.bookCoverUploadView.setOnClickListener(v -> imageUploadSelect());

        createBooksBinding.etBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validationViewModel.isInputEmpty(s.toString().trim())) {
                    createBooksBinding.tlBookName.setErrorEnabled(true);
                    createBooksBinding.tlBookName.setError("Book name is empty!");
                } else {
                    createBooksBinding.tlBookName.setErrorEnabled(false);
                    onChangeTitle(count >= 1 || before > 1 || start >= 1 ? s.toString().trim() : getString(R.string.createYourBookText));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createBooksBinding.createBookBtn.setOnClickListener(v -> {
            createBooksBinding.tlBookName.setErrorEnabled(false);

            name = Objects.requireNonNull(createBooksBinding.etBookName.getText()).toString().trim();
            info = Objects.requireNonNull(createBooksBinding.etBookInfo.getText()).toString().trim();
            if (validationViewModel.isInputEmpty(name)) {
                loadingFalse();
                createBooksBinding.tlBookName.setErrorEnabled(true);
                createBooksBinding.tlBookName.setError("Book name is empty!");
            } else if (coverPath == null) {
                loadingFalse();
                Toast.makeText(requireContext(), "Book cover require! but you can change anytime!", Toast.LENGTH_SHORT).show();
            } else {
                createBookViewModel.requestCreateBook(name, info, new File(coverPath));
            }
        });

    }

    private void loadingFalse() {
        createBooksBinding.createBookBtn.setLoading(false);
        createBooksBinding.createBookBtn.setText("বই তৈরি করুন");
    }

    private void imageUploadSelect() {
        if (Storage.hasStoragePermissions(requireActivity())) {
            // Permissions are granted, perform your storage-related operations here
            openImagePicker();
        } else {
            // Request permissions
            Storage.requestStoragePermissions(requireActivity());
        }
    }

    private void openImagePicker() {
        Intent intent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Storage.onRequestPermissionsResult(requestCode, grantResults, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied() {
                Storage.handlePermissionResult(requireActivity(), grantResults, new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        openImagePicker();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // An image is picked, load and display it in the ImageView
            try {
                // Load the selected bitmap image
                Uri imageUri = data.getData();

                Bitmap originalBitmap = BitmapUtils.loadBitmapFromUri(requireContext(), imageUri);

                if (BitmapUtils.isBitmapSizeExceedsLimit(originalBitmap)) {

                    // Option 1: Reduce the size of the image
                    Bitmap resizedBitmap = BitmapUtils.resizeBitmap(originalBitmap);
                    createBooksBinding.bookCoverUploadView.setImageBitmap(resizedBitmap);

                    // Option 2: Split the image into smaller parts
                    // TODO:: Only used for small box:::=> BitmapUtils.splitBitmap(originalBitmap);


                    // Now you can use the 'subBitmaps' array for drawing individual parts
                } else {
                    createBooksBinding.bookCoverUploadView.setImageBitmap(originalBitmap);
                    // Your code for drawing the original bitmap
                }

                // Cover path Also string
                coverPath = BitmapUtils.getRealPath(requireContext(), imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void onChangeTitle(String title) {
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }
}