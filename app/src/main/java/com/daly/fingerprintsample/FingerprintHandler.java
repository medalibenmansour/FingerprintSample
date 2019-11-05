package com.daly.fingerprintsample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context mContext;

    public FingerprintHandler(Context context) {
        this.mContext = context;
    }

    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an auth error !" + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication failed !", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error : " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app",true);
    }

    private void update(String message, boolean authSucceed) {
        TextView paraLabel = ((Activity)mContext).findViewById(R.id.paraLabel);
        ImageView fingerprintImage = ((Activity)mContext).findViewById(R.id.fingerprintImage);
        paraLabel.setText(message);

        if (!authSucceed) {
            paraLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            paraLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            fingerprintImage.setImageResource(R.mipmap.ic_done);
        }
    }
}
