package request.kodw;


import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.royalnext.base.util.Common;

import java.io.IOException;

import request.kodw.app.App;

public class GCMManager {
    static final String SENDER_ID = "786830972895";
    static GoogleCloudMessaging gcm;

    static public void init() {
        gcm = GoogleCloudMessaging.getInstance(App.me);
        String token = App.me.getPref("notificationToken");

        Common.i("notificationToken: " + token);
        if (token.isEmpty()) {
            registerInBackground();
        }
    }


    static private void registerInBackground() {
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected String doInBackground(Object... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(App.me);
                    }
                    String token = gcm.register(SENDER_ID);
                    Common.i("notificationToken: " + token);
                    App.me.savePref("notificationToken", token);
                } catch (IOException ex) {
                    Common.e(ex);
                }
                return msg;
            }

            @Override
            protected void onPostExecute(Object msg) {
            }
        }.execute(null, null, null);

    }
}
