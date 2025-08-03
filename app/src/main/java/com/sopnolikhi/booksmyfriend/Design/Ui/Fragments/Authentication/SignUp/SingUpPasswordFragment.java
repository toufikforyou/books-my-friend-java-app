package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.SignUp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyboardUtils;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.SignUp.SignUpViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Common.ValidationViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentSingUpPasswordBinding;

import java.util.Objects;

public class SingUpPasswordFragment extends Fragment {
    FragmentSingUpPasswordBinding passwordBinding;
    private NavController navController;
    private ValidationViewModel validationViewModel;
    private SignUpViewModel signUpViewModel;

    private String name, account, gender, dob, password, confirm;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        passwordBinding = FragmentSingUpPasswordBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sing_up_password, container, false);
        return passwordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (validationViewModel == null) {
            validationViewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        }

        if (signUpViewModel == null) {
            signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        }

        // get the bundle from the arguments
        bundle = getArguments();
        assert bundle != null;
        name = bundle.getString("name");
        account = bundle.getString("account");
        gender = bundle.getString("gender");
        dob = bundle.getString("dob");


        // TODO:: Observe the ViewModel SingUp response method
        signUpViewModel.getSignUpData().observe(getViewLifecycleOwner(), signUpResData -> {
            if (signUpResData instanceof ApiResponseModel.Loading) {
                passwordBinding.createAccountButton.setLoading(true);
            } else if (signUpResData instanceof ApiResponseModel.SuccessCode) {
                if (signUpResData.getSuccessCode() == 1001) {
                    bundle.putString("account", account);
                    bundle.putString("password", password);
                    navController.navigate(R.id.action_singUpPasswordFragment_to_loginFragment, bundle);
                    signUpViewModel.clearSignUpData();
                }
            } else if (signUpResData instanceof ApiResponseModel.ErrorCode) {
                passwordBinding.createAccountButton.setLoading(false);
                passwordBinding.createAccountButton.setText(getResources().getString(R.string.createYourAccountText));
                Toast.makeText(requireContext(), signUpResData.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // TODO:: Input Valid for user password
        passwordBinding.includePassword.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onValidPassword(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // TODO:: Input Valid for user confirm password
        passwordBinding.includePassword.etConformPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onValidConfirmPassword(Objects.requireNonNull(passwordBinding.includePassword.etPassword.getText()).toString().trim(), s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // TODO:: CREATE ACCOUNT BUTTON
        passwordBinding.createAccountButton.setText(getResources().getString(R.string.createYourAccountText));

        passwordBinding.createAccountButton.setOnClickListener(v -> {
            password = Objects.requireNonNull(passwordBinding.includePassword.etPassword.getText()).toString().trim();
            confirm = Objects.requireNonNull(passwordBinding.includePassword.etConformPassword.getText()).toString().trim();

            if (validationViewModel.passwordValid(password)) {
                passwordBinding.createAccountButton.setLoading(false);
                onValidPassword(password);
                KeyboardUtils.showKeyboard(passwordBinding.includePassword.etPassword);
            } else if (validationViewModel.passwordConfirm(password, confirm)) {
                passwordBinding.createAccountButton.setLoading(false);
                onValidConfirmPassword(password, confirm);
                KeyboardUtils.showKeyboard(passwordBinding.includePassword.etConformPassword);
            } else {
                KeyboardUtils.hideKeyboard(view);

                passwordBinding.createAccountButton.setText(getResources().getString(R.string.pleaseWaitText));

                signUpViewModel.getSignUpRequest(name, account, gender, dob, password);
            }
        });

        passwordBinding.backButton.setOnClickListener(v -> navController.navigateUp());

    }

    private void onValidConfirmPassword(String password, String confirm) {
        if (validationViewModel.isInputEmpty(confirm)) {
            passwordBinding.includePassword.tilConformPassword.setErrorEnabled(true);
            passwordBinding.includePassword.tilConformPassword.setError(getResources().getString(R.string.emptyFieldText));
        } else if (validationViewModel.passwordConfirm(password, confirm)) {
            passwordBinding.includePassword.tilConformPassword.setErrorEnabled(true);
            passwordBinding.includePassword.tilConformPassword.setError(getResources().getString(R.string.errorPasswordMatchText));
        } else {
            passwordBinding.includePassword.tilConformPassword.setErrorEnabled(false);
        }
    }

    private void onValidPassword(String password) {
        if (validationViewModel.isInputEmpty(password)) {
            passwordBinding.includePassword.tilPassword.setErrorEnabled(true);
            passwordBinding.includePassword.tilPassword.setError(getResources().getString(R.string.emptyFieldText));
        } else if (validationViewModel.passwordValid(password)) {
            passwordBinding.includePassword.tilPassword.setErrorEnabled(true);
            passwordBinding.includePassword.tilPassword.setError(getResources().getString(R.string.errorPasswordText));
        } else {
            passwordBinding.includePassword.tilPassword.setErrorEnabled(false);
        }
    }
}