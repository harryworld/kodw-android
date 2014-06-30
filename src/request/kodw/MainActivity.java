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

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    private WebView webView;
    private TextView scheduleView;
    private TextView iBeaconView;
    public String deviceId;
    public String appKey;
    public static final String webSite = "http://www.thegaragesociety.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        // Start coding here

        appKey = "jdFYjuCqWyCdrywPT";
        // Generate deviceId
        deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("KODW", "Device ID: " + deviceId);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("deviceId", deviceId);
        params.put("username", deviceId);
        params.put("appKey", appKey);

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
//                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }
        });
        webView.loadUrl(webSite);

        scheduleView = (TextView) findViewById(R.id.schedule);
        scheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(webSite);
            }
        });

        iBeaconView = (TextView) findViewById(R.id.ibeacon);
        iBeaconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://www.homesmartly.com/app/" + appKey + "/" + deviceId);
            }
        });
    }


}
