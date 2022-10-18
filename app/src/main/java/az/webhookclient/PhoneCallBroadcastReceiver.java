package az.webhookclient;


import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import static androidx.core.content.ContextCompat.getMainExecutor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class PhoneCallBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneCallBroadcastReceiver";
    public static final String PhoneCallWebhookURLKey = "call_wh_url";
    public static final String PhoneCallWebhookDefault = "http://192.168.1.1:8900/phone_call";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        if (intent.getAction() == null)
            return;
        if (intent.getExtras() == null)
            return;

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            System.out.println("outgoing call " + savedNumber + "   " + savedNumber);
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);


            Log.d(TAG, stateStr);
            Log.d(TAG, "number="+number);
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}