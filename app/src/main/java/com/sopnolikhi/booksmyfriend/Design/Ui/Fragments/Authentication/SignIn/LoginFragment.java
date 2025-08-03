package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.SignIn;

import android.content.Intent;
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

import com.sopnolikhi.booksmyfriend.MainActivity;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Extract.Extract;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyboardUtils;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Sessions.LoginSession;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.SignIn.SignInViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Common.ValidationViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentLoginBinding;

import java.util.Objects;

public class LoginFragment extends Fragment {
    FragmentLoginBinding loginBinding;
    private NavController navController;
    private LoginSession loginSession;
    private ValidationViewModel validationViewModel;
    private SignInViewModel signInViewModel;
    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);
//        inflater.inflate(R.layout.fragment_login, container, false);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (validationViewModel == null) {
            validationViewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        }

        if (signInViewModel == null) {
            signInViewModel = new ViewModelProvider(requireActivity()).get(SignInViewModel.class);
        }

        if (loginSession == null) {
            loginSession = new LoginSession(requireContext());
        }

        // get the bundle from the arguments
        bundle = getArguments();
        if (bundle != null) {
            loginBinding.include.etUsername.setText(bundle.getString("account"));
            loginBinding.include.etPassword.setText(bundle.getString("password"));
        }


        // TODO:: Observe the ViewModel Login response method
        signInViewModel.getLogInResult().observe(getViewLifecycleOwner(), logInResData -> {
            if (logInResData instanceof ApiResponseModel.Loading) {
                setEnableFalse();
                loginBinding.include.loginButton.setLoading(true);
                loginBinding.include.loginButton.setText(getResources().getString(R.string.pleaseWaitText));

            } else if (logInResData instanceof ApiResponseModel.SuccessCode) {
                loginBinding.include.loginButton.setLoading(false);
                loginBinding.include.loginButton.setEnabled(false);
                if (logInResData.getSuccessCode() == 1000) {
                    loginBinding.include.loginButton.setLoading(false);
                    loginBinding.include.loginButton.setEnabled(false);
                    loginBinding.include.loginButton.setText(getResources().getString(R.string.signInSuccessText));

                    loginSession.saveLoginUserInfo(logInResData.getData().getFullName(), logInResData.getData().getUserId(), logInResData.getData().getToken(), logInResData.getData().getExpired());

                    startActivity(new Intent(requireActivity(), MainActivity.class));
                    requireActivity().finish();

                }

            } else if (logInResData instanceof ApiResponseModel.ErrorCode) {
                setEnableTrue();
                setErrorFalse();
                loginBinding.include.loginButton.setLoading(false);
                loginBinding.include.loginButton.setText(getResources().getString(R.string.signInText));

                int subCode = Extract.ExtractDigit(logInResData.getMessage());

                String message = Extract.ExtractMessage(logInResData.getMessage());

                if (logInResData.getErrorCode() == 1050) {
                    switch (subCode) {
                        case 701:
                            loginBinding.include.tilUsername.setError(message);
                            loginBinding.include.tilPassword.setError(message);
                            break;
                        case 705:
                            loginBinding.include.tilPassword.setError(message);
                            break;
                        default:
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } else if (logInResData.getErrorCode() == 1052) {
                    loginBinding.include.tilPassword.setError(message);
                } else if (logInResData.getErrorCode() == 1053) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                } else if (logInResData.getErrorCode() == 1054) {
                    loginBinding.include.tilUsername.setError(message);
                } else {
                    Toast.makeText(requireContext(), message + subCode, Toast.LENGTH_SHORT).show();
                }
            }
        });


        // TODO:: Input Valid for user account
        loginBinding.include.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                voidUserNameError(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // TODO:: Input Valid for user password
        loginBinding.include.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                voidPasswordError(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // TODO:: logIn Button Click to sing in request in mvvm
        loginBinding.include.loginButton.setText(getResources().getString(R.string.signInText));

        loginBinding.include.loginButton.setOnClickListener(v -> {
            String account = Objects.requireNonNull(loginBinding.include.etUsername.getText()).toString().trim();
            String password = Objects.requireNonNull(loginBinding.include.etPassword.getText()).toString().trim();

            if (validationViewModel.accountValid(account)) {
                KeyboardUtils.showKeyboard(loginBinding.include.etUsername);
                loginBinding.include.loginButton.setLoading(false);
                voidUserNameError(account);
            } else if (validationViewModel.passwordValid(password)) {
                KeyboardUtils.showKeyboard(loginBinding.include.etPassword);
                loginBinding.include.loginButton.setLoading(false);
                voidPasswordError(password);
            } else {
                KeyboardUtils.hideKeyboard(view);
                setErrorFalse();
                signInViewModel.getLogInRequest(account, password, false);
            }
        });

        loginBinding.include.createAccount.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_singUpFragment));

        loginBinding.include.forgotPassword.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_accountFinderFragment));
    }

    private void voidPasswordError(String password) {
        if (validationViewModel.isInputEmpty(password)) {
            loginBinding.include.tilPassword.setErrorEnabled(true);
            loginBinding.include.tilPassword.setError("Password is empty.");

        } else if (validationViewModel.passwordValid(password)) {
            loginBinding.include.tilPassword.setErrorEnabled(true);
            loginBinding.include.tilPassword.setError("Password is not valid. Must be 8 digit.");

        } else {
            loginBinding.include.tilPassword.setErrorEnabled(false);
        }
    }

    private void voidUserNameError(String account) {
        if (validationViewModel.isInputEmpty(account)) {
            loginBinding.include.tilUsername.setErrorEnabled(true);
            loginBinding.include.tilUsername.setError("Email or phone or username is empty.");
        } else if (validationViewModel.accountValid(account)) {
            loginBinding.include.tilUsername.setErrorEnabled(true);
            loginBinding.include.tilUsername.setError("Email or phone or username is not valid.");

        } else {
            loginBinding.include.tilUsername.setErrorEnabled(false);
        }
    }

    private void setEnableFalse() {
        loginBinding.include.tilUsername.setEnabled(false);
        loginBinding.include.tilPassword.setEnabled(false);
    }

    private void setEnableTrue() {
        loginBinding.include.tilUsername.setEnabled(true);
        loginBinding.include.tilPassword.setEnabled(true);
    }

    private void setErrorFalse() {
        loginBinding.include.tilUsername.setErrorEnabled(false);
        loginBinding.include.tilPassword.setErrorEnabled(false);
    }
}