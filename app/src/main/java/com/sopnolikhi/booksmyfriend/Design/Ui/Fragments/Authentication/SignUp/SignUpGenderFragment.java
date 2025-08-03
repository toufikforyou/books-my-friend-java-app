package com.sopnolikhi.booksmyfriend.Design.Ui.Fragments.Authentication.SignUp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sopnolikhi.booksmyfriend.R;
import com.sopnolikhi.booksmyfriend.databinding.FragmentSignUpGenderBinding;

import java.util.Calendar;

public class SignUpGenderFragment extends Fragment {

    FragmentSignUpGenderBinding genderBinding;
    private NavController navController;
    private int year, month, day;

    private String name, account, gender, dob;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        genderBinding = FragmentSignUpGenderBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sign_up_gender, container, false);
        return genderBinding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        validAge();

        // get the bundle from the arguments
        bundle = getArguments();
        assert bundle != null;
        name = bundle.getString("name");
        account = bundle.getString("account");


        genderBinding.nextButton.setText(getResources().getString(R.string.nextText));
        genderBinding.nextButton.setOnClickListener(v -> {
            if (!validGender()) {
                Toast.makeText(getContext(), getResources().getString(R.string.selectGenderAgeText), Toast.LENGTH_SHORT).show();
            } else {
                int selectedIndex = genderBinding.includeGender.genderG.indexOfChild(view.findViewById(genderBinding.includeGender.genderG.getCheckedRadioButtonId()));

                gender = selectedIndex == 0 ? "Male" : selectedIndex == 1 ? "Female" : "Other";

                dob = String.format("%04d-%02d-%02d", genderBinding.includeGender.userPickerDate.getYear(), genderBinding.includeGender.userPickerDate.getMonth() + 1, genderBinding.includeGender.userPickerDate.getDayOfMonth());

                genderBinding.nextButton.setText(getResources().getString(R.string.pleaseWaitText));

                bundle.putString("name", name);
                bundle.putString("account", account);
                bundle.putString("gender", gender);
                bundle.putString("dob", dob);

                navController.navigate(R.id.action_signUpGenderFragment_to_singUpPasswordFragment, bundle);
            }
            genderBinding.nextButton.setLoading(false);
        });

        genderBinding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private boolean validGender() {
        return genderBinding.includeGender.genderG.getCheckedRadioButtonId() != -1;
    }

    private void validAge() {
        // Get selected date
        year = genderBinding.includeGender.userPickerDate.getYear();
        month = genderBinding.includeGender.userPickerDate.getMonth();
        day = genderBinding.includeGender.userPickerDate.getDayOfMonth();

        // Set maximum date
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, -5);
        genderBinding.includeGender.userPickerDate.setMaxDate(maxDate.getTimeInMillis());
        // set up user picker date;
        Calendar selectedDate = Calendar.getInstance();

        selectedDate.set(year, month, day);
    }
}