package com.sopnolikhi.booksmyfriend.Services.Networks.Payment;

import android.content.Context;

import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCLanguage;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.Objects;

public class SSLCommerzPayment {
    private static final String PAYMENT_SSL_STORE_ID = "sopno658041d713523"; // Your store ID
    private static final String PAYMENT_SSL_STORE_PASSWORD = "sopno658041d713523@ssl"; // Your store password
    private final Context context;

    public SSLCommerzPayment(Context context) {
        this.context = context;
    }

    // Initialize SSLCommerz
    private SSLCommerzInitialization initializeSSLCommerz(Double amount, String transitionId, String productName) {
        return new SSLCommerzInitialization(PAYMENT_SSL_STORE_ID, PAYMENT_SSL_STORE_PASSWORD, amount, SSLCCurrencyType.BDT, transitionId, productName, SSLCSdkType.TESTBOX, Objects.equals(UserSetting.SETTING_LANGUAGE_USER_SET, "bn") ? SSLCLanguage.Bangla : SSLCLanguage.English);
    }

    // Make a payment
    public void makePayment(double amount, String invoiceId, String productName, final SSLCTransactionResponseListener listener) {
        SSLCommerzInitialization initialization = initializeSSLCommerz(amount, invoiceId, productName);

        IntegrateSSLCommerz.getInstance(context).addSSLCommerzInitialization(initialization).buildApiCall(new SSLCTransactionResponseListener() {
            @Override
            public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
                // Handle transaction success
                // Perform actions after a successful transaction
                listener.transactionSuccess(sslcTransactionInfoModel);
            }

            @Override
            public void transactionFail(String error) {
                // Handle transaction failure
                // Perform actions if the transaction fails
                listener.transactionFail(error);
            }

            @Override
            public void closed(String message) {
                // Handle transaction closure
                // Perform actions when the transaction is closed
                listener.closed(message);
            }
        });
    }
}


