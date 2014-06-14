package request.kodw;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends Activity {

    private WebView webView;
    private TextView scheduleView;
    private TextView iBeaconView;
    public String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start coding here

        // Generate deviceId
        deviceId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

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
