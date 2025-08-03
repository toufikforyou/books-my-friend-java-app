package com.sopnolikhi.booksmyfriend.Design.Dialogs.Exists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.sopnolikhi.booksmyfriend.R;

public class AlertDialogBox {
    Context context;
    ClickDialog clickDialog;

    public AlertDialogBox(Context context, ClickDialog clickDialog) {
        this.context = context;
        this.clickDialog = clickDialog;
    }

    public void permissionSetting() {

        AlertDialog.Builder exitAlert = new AlertDialog.Builder(context);
        // Title set
        exitAlert.setTitle("Alert Dialog Application.");
        // Massage Set
        exitAlert.setMessage("আপনি কি অ্যাপ থেকে প্রস্থান করার বিষয়ে নিশ্চিত?");
        // Icon set
        exitAlert.setIcon(R.drawable.question_mark);

        // exitAlert.setIconAttribute(R.style.app:drawableTint="?attr/colorSecondary")

        // Positive Button
        exitAlert.setCancelable(false);

        exitAlert.setPositiveButton("হ্যাঁ", (dialogInterface, i) -> {
            clickDialog.onYesClick();
        });
        exitAlert.setNegativeButton("না", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = exitAlert.create();
        alertDialog.show();

    }

    public void exitAlertDialogBox() {
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.inc_custom_alert_dialog_box, null, false);


        exitAlert.setView(dialogView);

        exitAlert.setCancelable(true);

        exitAlert.setNeutralButton("রেটিং দিব", (dialogInterface, i) -> clickDialog.opinionOurApplication());

        exitAlert.setPositiveButton("হ্যাঁ", (dialogInterface, i) -> clickDialog.onYesClick());

        exitAlert.setNegativeButton("না", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = exitAlert.create();
        alertDialog.show();
    }
}
