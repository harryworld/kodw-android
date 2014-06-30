package request.kodw.app;

import com.royalnext.base.app.BaseApp;

import request.kodw.GCMManager;

/**
 * Created by harryng on 14/6/14.
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();

        GCMManager.init();
    }
}
