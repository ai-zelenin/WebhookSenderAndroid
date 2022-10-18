package az.webhookclient;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WebhookTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "WebhookTask";
    private final String url;
    private final JSONObject jsonObject;

    public WebhookTask(String url, Map<String, Object> map) {
        super();
        this.url = url;
        this.jsonObject = new JSONObject(map);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(this.jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            httpConnection.getResponseCode();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Response", "" + s);
    }
}


