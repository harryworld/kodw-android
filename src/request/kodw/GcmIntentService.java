package request.kodw;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.royalnext.base.util.Common;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Common.i("onHandleIntent: " + intent.getExtras());
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendNotification(extras);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(Bundle extras) {
        Common.i("sendNotification: " + extras);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        String msg = extras.getString("msg");
        String type = extras.getString("t");
//        if (type.equals("CR") || type.equals("INVITE")) {
//            String thing_id = extras.getString("thing_id");
//            Intent intent = new Intent(this, ClubChatActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("target_id", thing_id);
//            contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//            B6App.me.eventNotification("RELOAD", "chatroom:" + thing_id);
//            if (B6App.me.doing != null && B6App.me.doing.equals("chatroom:" + thing_id)) {
//                return;
//            }
//        }
//        if (type.equals("PM")) {
//            String user_id = extras.getString("user_id");
//            Intent intent = new Intent(this, UserChatActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("target_id", user_id);
//            contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//            B6App.me.eventNotification("RELOAD", "chatroom:" + user_id);
//            if (B6App.me.doing != null && B6App.me.doing.equals("chatroom:" + user_id)) {
//                return;
//            }
//        }
//        if (type.equals("COMMENT") || type.equals("REPLY") || type.equals("LIKE")) {
//            String post_id = extras.getString("post_id");
//            Intent intent = new Intent(this, PostHomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra("target_id", post_id);
//            contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        }

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setSmallIcon(R.drawable.icon_b6);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
//        mBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/n"));
        long[] pattern = {0, 100, 200};
        mBuilder.setVibrate(pattern);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
