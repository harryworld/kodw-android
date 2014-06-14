package request.kodw;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.royalnext.base.activity.BaseActivity;
import com.royalnext.base.util.network.ApiCache;
import com.royalnext.base.util.network.ServerTaskListener;
import com.royalnext.base.util.network.ServerTaskManager;

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    private WebView webView;
    private TextView scheduleView;
    private TextView iBeaconView;
    public String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        // Start coding here

        // Generate deviceId
        deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("KODW", "Device ID: " + deviceId);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("username", deviceId);
        params.put("appId", "jnFcGCugEqTXpTu66");

        stm.startTask("http://api.homesmartly.com:8080/collectionapi/members", params, new ServerTaskListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(String result) {
                Log.d("KODW", "Success: " + result);
            }

            @Override
            public void onError(String result, String code, String msg) {
                Log.d("KODW", "Error code: " + code + " / " + msg);
            }
        }, ApiCache.FLAG_NO_CACHE, ServerTaskManager.GET_TYPE, null);

        // Shared WebView
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("Debug", url);
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }
        });
        webView.loadUrl("http://mobile.kodw.org/");

        scheduleView = (TextView) findViewById(R.id.schedule);
        scheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://mobile.kodw.org/");
            }
        });

        iBeaconView = (TextView) findViewById(R.id.ibeacon);
        iBeaconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://www.homesmartly.com/mobile/" + deviceId);
            }
        });
    }


}
