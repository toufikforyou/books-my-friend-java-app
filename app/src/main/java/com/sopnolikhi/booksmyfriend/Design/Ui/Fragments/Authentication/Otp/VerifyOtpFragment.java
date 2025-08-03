package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.Otp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputEditText;
import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.Services.Includes.Countdown.TimerCount;
import com.sopnolikhi.booksmyfriend.Services.Includes.Countdown.TimerInterface;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyValue;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.KeyboardUtils;
import com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse.ApiResponseModel;
import com.sopnolikhi.booksmyfriend.Services.Notifications.Otp.SmsAutoBroadcastReceiver;
import com.sopnolikhi.booksmyfriend.Services.Notifications.Otp.SmsAutoReceiverListener;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Authentications.Otp.OtpViewModel;
import com.sopnolikhi.booksmyfriend.ViewModel.Account.Common.ValidationViewModel;
import com.sopnolikhi.booksmyfriend.databinding.FragmentVerifyOtpBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyOtpFragment extends Fragment implements TimerInterface {
    private NavController navController;
    private SmsAutoBroadcastReceiver smsAutoBroadcastReceiver;
    private FragmentVerifyOtpBinding otpBinding;
    private ValidationViewModel validationViewModel;
    private OtpViewModel otpViewModel;
    private String name, account;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        otpBinding = FragmentVerifyOtpBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_verify_otp, container, false);
        return otpBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (validationViewModel == null) {
            validationViewModel = new ViewModelProvider(requireActivity()).get(ValidationViewModel.class);
        }

        if (otpViewModel == null) {
            otpViewModel = new ViewModelProvider(requireActivity()).get(OtpViewModel.class);
        }

        // get the bundle from the arguments
        bundle = getArguments();
        assert bundle != null;
        name = bundle.getString("name");
        account = bundle.getString("account");

        // TODO:: Observe the ViewModel otp send again register next step  response method
        otpViewModel.sendOtpResponse().observe(getViewLifecycleOwner(), otpResDataApiRes -> {
            if (otpResDataApiRes instanceof ApiResponseModel.SuccessCode) {
                if (otpResDataApiRes.getSuccessCode() == 1001) {
                    startTimerContDown();
                    otpViewModel.clearSendData();
                }
            }
        });

        // TODO:: Observe the ViewModel otp verify register next step  response method
        otpViewModel.verifyOtpResponse().observe(getViewLifecycleOwner(), otpResVerifyData -> {
            if (otpResVerifyData instanceof ApiResponseModel.Loading) {
                otpBinding.verifyOtpButton.setLoading(true);
            } else if (otpResVerifyData instanceof ApiResponseModel.SuccessCode) {
                if (otpResVerifyData.getSuccessCode() == 1000) {
                    bundle.putString("name", name);
                    bundle.putString("account", account);
                    navController.navigate(R.id.action_verifyOtpFragment_to_signUpGenderFragment, bundle);

                    otpViewModel.clearVerifyData();
                }
            } else if (otpResVerifyData instanceof ApiResponseModel.ErrorCode) {
                otpBinding.verifyOtpButton.setLoading(false);
                otpBinding.verifyOtpButton.setText(getResources().getString(R.string.verifyText));
                Toast.makeText(requireContext(), otpResVerifyData.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        KeyboardUtils.showKeyboard(otpBinding.otp1);
        setupOtpTextWatchers();

        otpBinding.verifyOtpButton.setText(getResources().getString(R.string.verifyText));

        otpBinding.otpDetailsText.setText(String.format("A verification code has been sent to your account. %s Verify the code below.", account));

        otpBinding.verifyOtpButton.setOnClickListener(v -> {

            String otp1 = Objects.requireNonNull(otpBinding.otp1.getText()).toString().trim();
            String otp2 = Objects.requireNonNull(otpBinding.otp2.getText()).toString().trim();
            String otp3 = Objects.requireNonNull(otpBinding.otp3.getText()).toString().trim();
            String otp4 = Objects.requireNonNull(otpBinding.otp4.getText()).toString().trim();
            String otp5 = Objects.requireNonNull(otpBinding.otp5.getText()).toString().trim();

            if (validationViewModel.isInputEmpty(otp1)) {
                focusKeyboard(otpBinding.otp1);
            } else if (validationViewModel.isInputEmpty(otp2)) {
                focusKeyboard(otpBinding.otp2);
            } else if (validationViewModel.isInputEmpty(otp3)) {
                focusKeyboard(otpBinding.otp3);
            } else if (validationViewModel.isInputEmpty(otp4)) {
                focusKeyboard(otpBinding.otp4);
            } else if (validationViewModel.isInputEmpty(otp5)) {
                focusKeyboard(otpBinding.otp5);
            } else {
                KeyboardUtils.hideKeyboard(view);

                otpBinding.verifyOtpButton.setText(getResources().getString(R.string.pleaseWaitText));

                otpViewModel.verifyOtpRequest(account, otp1 + otp2 + otp3 + otp4 + otp5);
            }

        });

        // TODO: Automatic receive sms and continue next step;
        autoSmsReceiver();


        // TODO:: Change on text



    }

    private void autoSmsReceiver() {
        SmsRetrieverClient smsRetrieverClient = SmsRetriever.getClient(requireContext());
        smsRetrieverClient.startSmsUserConsent(null);
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void registerBroadcast() {
        if (smsAutoBroadcastReceiver == null) {
            smsAutoBroadcastReceiver = new SmsAutoBroadcastReceiver();
        }

        smsAutoBroadcastReceiver.intListener(new SmsAutoReceiverListener() {
            @Override
            public void onSuccessReadSms(Intent intent) {
                startActivityForResult(intent, KeyValue.SMS_AUTO_FILLED_CODE);
            }

            @Override
            public void onErrorReadSms() {

            }
        });

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        requireActivity().registerReceiver(smsAutoBroadcastReceiver, intentFilter);
    }

    private void focusKeyboard(View view) {
        otpBinding.verifyOtpButton.setLoading(false);
        KeyboardUtils.showKeyboard(view);
    }

    private void startTimerContDown() {
        TimerCount.startTimer(180, this);
    }

    @Override
    public void onUpdateTime(String time) {
        otpBinding.sendAgain.setClickable(false);
        otpBinding.sendAgain.setText(time);
    }

    @Override
    public void onUpdateFinish() {
        otpBinding.sendAgain.setText(getResources().getString(R.string.sendAgainCodeText));
        otpBinding.sendAgain.setClickable(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        registerBroadcast();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (smsAutoBroadcastReceiver != null) {
            requireActivity().unregisterReceiver(smsAutoBroadcastReceiver);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        TimerCount.cancelTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TimerCount.cancelTimer();
    }

    private void setupOtpTextWatchers() {
        otpBinding.otp1.addTextChangedListener(createOtpTextWatcher(otpBinding.otp1, otpBinding.otp2, null));
        otpBinding.otp2.addTextChangedListener(createOtpTextWatcher(otpBinding.otp2, otpBinding.otp3, otpBinding.otp1));
        otpBinding.otp3.addTextChangedListener(createOtpTextWatcher(otpBinding.otp3, otpBinding.otp4, otpBinding.otp2));
        otpBinding.otp4.addTextChangedListener(createOtpTextWatcher(otpBinding.otp4, otpBinding.otp5, otpBinding.otp3));
        otpBinding.otp5.addTextChangedListener(createOtpTextWatcher(otpBinding.otp5, null, otpBinding.otp4));
    }

    private TextWatcher createOtpTextWatcher(final TextInputEditText currentView, final TextInputEditText nextView, final TextInputEditText previousView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1 && nextView != null) {
                    // If the length is 1, move focus to the next TextInputEditText
                    KeyboardUtils.showKeyboard(nextView);
                } else if (editable.length() == 0 && previousView != null) {
                    // If the length is 0, move focus to the previous TextInputEditText
                    KeyboardUtils.showKeyboard(previousView);
                } else {
                    KeyboardUtils.hideKeyboard(currentView);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == KeyValue.SMS_AUTO_FILLED_CODE) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String smsMessage = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpMessage(smsMessage);
            }
        }
    }

    private void getOtpMessage(String smsMessage) {
        Matcher matcher = Pattern.compile("(^|\\D)\\d{5}(\\D|$)").matcher(smsMessage);
        if (matcher.find()) {
            String otp = matcher.group().replaceAll("\\D", ""); // Extract only digits from the matched text

            if (otp.length() >= 5) {
                otpBinding.otp1.setText(String.valueOf(otp.charAt(0)));
                otpBinding.otp2.setText(String.valueOf(otp.charAt(1)));
                otpBinding.otp3.setText(String.valueOf(otp.charAt(2)));
                otpBinding.otp4.setText(String.valueOf(otp.charAt(3)));
                otpBinding.otp5.setText(String.valueOf(otp.charAt(4)));
            }
        }
    }

}


