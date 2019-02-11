package localization.com.bluetoothchat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import localization.com.bt_fp.R;

/**
 * Created by whit3hawks on 11/16/16.
 */
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString);

        mlAuthListner.onSucess("Fingerprint Authentication error\n" + errString);

    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString);

        mlAuthListner.onSucess("Fingerprint Authentication help\n" + helpString);

    }

    @Override
    public void onAuthenticationFailed() {
        mlAuthListner.onSucess("Fingerprint Authentication failed. ");

        this.update("");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context,"Sucess",Toast.LENGTH_LONG).show();
        mlAuthListner.onSucess("Finger Print State  Sucess ");



    }

    private void update(String e){
        /*TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);*/


        Toast.makeText(context,e,Toast.LENGTH_LONG).show();


    }

    static AuthListner mlAuthListner;
    public static void setMlAuthListner(AuthListner mlAuthListner_) {
        mlAuthListner = mlAuthListner_;
    }

    public interface AuthListner {
         void onSucess(String text);
    }

}
