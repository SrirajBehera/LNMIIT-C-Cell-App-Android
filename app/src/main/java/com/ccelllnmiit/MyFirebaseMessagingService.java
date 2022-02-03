package com.ccelllnmiit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    User us;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        us=new User(getApplicationContext());

        final String TAG="mm";
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            ArrayList<String> list=new ArrayList<String>();
            list=us.getDetSet();
            if(!list.contains(remoteMessage.getData().get("title")+"Φ"+remoteMessage.getData().get("details")))
            {list.add(0,remoteMessage.getData().get("title")+"Φ"+remoteMessage.getData().get("details"));
            ArrayList<String> listb=new ArrayList<String>();
            listb=us.getBulSet();
            listb.add(0,"true");
            us.update_prof(list);
            us.update_bul(listb);}
            BulletinBoard.isUpdate=true;

            if(MainActivity.isFront)
            {
                Intent intent = new Intent("unique_name");

                //put whatever data you want to send, if any
                intent.putExtra("message","update");

                //send broadcast
                getApplicationContext().sendBroadcast(intent);
            }

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.rsz_c_cell_logo)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("details")))
                            .setContentTitle(remoteMessage.getData().get("title"))
                            .setContentInfo("New Update in Bulletin Board")
                            .setContentText(remoteMessage.getData().get("details"));

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            builder.setAutoCancel(true).setSound(uri);
            builder.setContentIntent(contentIntent);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel("10001", "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                assert manager != null;
                builder.setChannelId("10001");
                manager.createNotificationChannel(notificationChannel);
            }

            // Add as notification
            manager.notify(0, builder.build());

           /* if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
