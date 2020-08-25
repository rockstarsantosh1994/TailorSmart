package com.praxello.tailorsmart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebaseMsgService";
    App app;
    Context mContext;

    @Override
    public void onNewToken(String s) {
        app = (App) getApplication();
        if (!TextUtils.isEmpty(s))
            app.getPreferences().setToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        app = (App) getApplication();
        mContext = getApplicationContext();
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Map<String, String> data = remoteMessage.getData();
            sendNotification(remoteMessage.getNotification().getBody(), mContext.getString(R.string.app_name), data);
        }
    }

    private void sendNotification(String message, String title, Map<String, String> data) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create Channel for Push notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "id_" + mContext.getResources().getString(R.string.app_name).toLowerCase(); // The id of the channel.
            CharSequence name = getString(R.string.notification_channel_name); // The user-visible name of the channel.
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent;
        if (app.getPreferences().isLoggedInUser()) {
            intent = new Intent(this, MainActivity.class);
            if (data != null) {
                intent.putExtra("type", data.get("type"));
                intent.putExtra("msg", message);
            }
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Notification n;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "id_tailorsmart");
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setLargeIcon(largeIcon);
//            notificationBuilder.setSmallIcon(R.mipmap.app_icon_noti);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        notificationBuilder.setVibrate(new long[]{1000, 1000});
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        notificationBuilder.setContentTitle(getResources().getString(R.string.app_name));
        notificationBuilder.setContentIntent(pendingIntent);

        n = notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message)).build();
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        //Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);

        if (notificationManager != null) {
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;
            notificationManager.notify(m /* ID of notification */, notificationBuilder.build());
        }
    }
}
