package az.webhookclient;


import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.Map;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSBroadcastReceiver";
    public static final String SMSWebhookURLKey = "sms_wh_url";
    public static final String SMSWebhookDefault = "http://192.168.1.1:8900/sms";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String url = prefs.getString(SMSWebhookURLKey, SMSWebhookDefault);
        SmsMessage[] messages = getMessagesFromIntent(intent);
        for (SmsMessage smsMessage : messages) {
            Map<String, Object> m = new HashMap<>();
            m.put("type", "sms");
            m.put("body", smsMessage.getMessageBody());
            m.put("origin", smsMessage.getOriginatingAddress());
            m.put("class", smsMessage.getMessageClass().toString());
            m.put("index_icc", smsMessage.getIndexOnIcc());
            m.put("pdu_hex", byteArrayToHex(smsMessage.getPdu()));
            new WebhookTask(url, m).execute();
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}