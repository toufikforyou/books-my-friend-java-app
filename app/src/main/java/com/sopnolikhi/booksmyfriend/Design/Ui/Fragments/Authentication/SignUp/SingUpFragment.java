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
import com.sopnolikhi.booksmyfriend.Services.Includes.Extract.Extract;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyboardUtils;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.CheckInfo.UserInfoViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.Otp.OtpViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Common.ValidationViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentSingUpBinding;

import java.util.Objects;

public class SingUpFragment extends Fragment {
    FragmentSingUpBinding singUpBinding;
    private NavController navController;

    private ValidationViewModel validationViewModel;

    private UserInfoViewModel userInfoViewModel;
    private OtpViewModel otpViewModel;
    private Bundle bundle;
    private String name, account;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        singUpBinding = FragmentSingUpBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sing_up, container, false);
        return singUpBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (validationViewModel == null) {
            validationViewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        }

        if (userInfoViewModel == null) {
            userInfoViewModel = new ViewModelProvider(requireActivity()).get(UserInfoViewModel.class);
        }

        if (otpViewModel == null) {
            otpViewModel = new ViewModelProvider(requireActivity()).get(OtpViewModel.class);
        }

        // TODO:: Bundle for passing data other fragment;
        bundle = new Bundle();

        // TODO:: Observe the ViewModel user check response method
        userInfoViewModel.getAccountInfoData().observe(getViewLifecycleOwner(), InfoResData -> {
            if (InfoResData instanceof ApiResponseModel.Loading) {
                singUpBinding.nextButton.setLoading(true);
            } else if (InfoResData instanceof ApiResponseModel.SuccessCode) {
                singUpBinding.nextButton.setLoading(false);
                if (InfoResData.getSuccessCode() == 1000) {
                    String smg = "User already Registered!";
                    if (InfoResData.getData().getAccount() == 1) {
                        smg += " And Your Account is active now. You can get login an account.";
                    } else if (InfoResData.getData().getAccount() == 2) {
                        smg += " And Your Account is de-active now. please contact us support@sopnolikhi.com";
                    } else {
                        smg += " And account has suspended. Please contact us support@sopnolikhi.com";
                    }
                    singUpBinding.include.tilEmailMobile.setError(smg);
                }
            } else if (InfoResData instanceof ApiResponseModel.ErrorCode) {
                singUpBinding.nextButton.setLoading(false);

                int subCode = Extract.ExtractDigit(InfoResData.getMessage());
                String message = Extract.ExtractMessage(InfoResData.getMessage());

                if (InfoResData.getErrorCode() == 1050) {
                    if (subCode == 701) {
                        singUpBinding.include.tilEmailMobile.setError(message);
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } else if (InfoResData.getErrorCode() == 1054) {
                    singUpBinding.include.tilEmailMobile.setErrorEnabled(false);

                    otpViewModel.sendOtpRequest(name, account);
                } else {
                    Toast.makeText(requireContext(), subCode + message, Toast.LENGTH_SHORT).show();
                }
            }

        });

        // TODO:: Observe the ViewModel user not account register next step  response method
        otpViewModel.sendOtpResponse().observe(getViewLifecycleOwner(), sendOtpResponse -> {
            if (sendOtpResponse instanceof ApiResponseModel.Loading) {
                singUpBinding.nextButton.setLoading(true);
            } else if (sendOtpResponse instanceof ApiResponseModel.SuccessCode) {
                if (sendOtpResponse.getSuccessCode() == 1001) {
                    bundle.putString("name", name);
                    bundle.putString("account", account);

                    navController.navigate(R.id.action_singUpFragment_to_verifyOtpFragment, bundle);

                    userInfoViewModel.clearOtpMutableLiveData();
                }
            } else if (sendOtpResponse instanceof ApiResponseModel.ErrorCode) {
                singUpBinding.nextButton.setLoading(false);

                Toast.makeText(requireContext(), sendOtpResponse.getErrorCode() + sendOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // TODO:: Input Valid for user full name
        singUpBinding.include.etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validFullName(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // TODO:: Input Valid for use email or mobile
        singUpBinding.include.etEmailMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validEmailPhone(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        singUpBinding.nextButton.setOnClickListener(v -> {
            name = Objects.requireNonNull(singUpBinding.include.etFullName.getText()).toString().trim();
            account = Objects.requireNonNull(singUpBinding.include.etEmailMobile.getText()).toString().trim();
            if (validationViewModel.isNameValid(Objects.requireNonNull(name))) {
                singUpBinding.nextButton.setLoading(false);
                validFullName(name);
                KeyboardUtils.showKeyboard(singUpBinding.include.etFullName);
            } else if (validationViewModel.isEmailOrMobileValid(account)) {
                singUpBinding.nextButton.setLoading(false);
                validEmailPhone(account);
                KeyboardUtils.showKeyboard(singUpBinding.include.etEmailMobile);
            } else {
                KeyboardUtils.hideKeyboard(view);
                singUpBinding.nextButton.setText(getResources().getString(R.string.pleaseWaitText));

                userInfoViewModel.requestAccountInfo(account);
            }
        });

        singUpBinding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private void validFullName(String name) {
        if (validationViewModel.isInputEmpty(name)) {
            singUpBinding.include.tilFullName.setErrorEnabled(true);
            singUpBinding.include.tilFullName.setError(getResources().getString(R.string.emptyFieldText));

        } else if (validationViewModel.isNameValid(name)) {
            singUpBinding.include.tilFullName.setErrorEnabled(true);
            singUpBinding.include.tilFullName.setError(getResources().getString(R.string.invalidFullNameText));

        } else {
            singUpBinding.include.tilFullName.setErrorEnabled(false);
        }
    }

    private void validEmailPhone(String emailPhone) {
        if (validationViewModel.isInputEmpty(emailPhone)) {
            singUpBinding.include.tilEmailMobile.setErrorEnabled(true);
            singUpBinding.include.tilEmailMobile.setError(getResources().getString(R.string.emptyFieldText));
        } else if (validationViewModel.isEmailOrMobileValid(emailPhone)) {
            singUpBinding.include.tilEmailMobile.setErrorEnabled(true);
            singUpBinding.include.tilEmailMobile.setError(getResources().getString(R.string.invalidMobileEmailText));

        } else {
            singUpBinding.include.tilEmailMobile.setErrorEnabled(false);
        }
    }
}