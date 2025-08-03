package com.sopnolikhi.booksmyfriend.Services.Includes.Utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {
    public static void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        assert imm != null;
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
