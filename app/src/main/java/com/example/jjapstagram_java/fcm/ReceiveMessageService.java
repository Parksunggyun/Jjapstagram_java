package com.example.jjapstagram_java.fcm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.jjapstagram_java.Jjapplication;
import com.example.jjapstagram_java.MainActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.login.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReceiveMessageService extends FirebaseMessagingService {

    private static final String TAG = ReceiveMessageService.class.getSimpleName();
    private String NOTIFICATION_CHANNEL_ID = "ATM_DEV";



    // 서버로부터 메시지 받았을 때
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, remoteMessage.getFrom());
        // remoteMessage.getData != null 일 때
        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData());
            Log.d(TAG, "remoteMessage.getData()");
        }

        // remoteMessage.getNotification != null 일 때
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification());
            Log.d(TAG, "remoteMessage.getNotification()");
        }
        /*
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (*//* Check if data needs to be processed by long running job *//*true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }*/
    }

    //토큰이 새롭게 만들어졌을 때 호출된다.
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token :" + token);
        sendRegistrationToServer(token);
    }

    /**
     *
     * @param notification 서버에서 보낸 알림 메시지 ( 항상 축약형이기 때문에 Head Up Notification 은 foreground 에서만 뜬다. )
     */
    private void sendNotification(RemoteMessage.Notification notification) {
        showNotification("Wow holy!", notification.getTitle(), notification.getBody(), "info");
    }

    /**
     * @param data 서버에서 보낸 데이터 메시지
     */
    private void sendNotification(Map<String, String> data) {
        String imagePath = data.get("imagePath");
        Set<Map.Entry<String, String>> entry = data.entrySet();
        Iterator<Map.Entry<String, String>> iter = entry.iterator();
        String title = "";
        String body = "";
        int count = 0;
        while (iter.hasNext()) {
            Map.Entry<String, String> eryt = iter.next();
            if(count == 0) {
                title = eryt.getKey();
                body = eryt.getValue();
            }
            count++;
        }
        showNotification(imagePath,"Wow holy!", title, body, "info");
    }

    private void showNotification(String imagePath, String... information) {

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            manager.createNotificationChannel(createChannel());
        }

        Intent intent = new Intent();
        if (Jjapplication.getUserInfo() != null) {
            intent.setClass(this, MainActivity.class);
        } else {
            intent.setClass(this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL) // 진동 및 소리
                .setPriority(NotificationCompat.PRIORITY_MAX) //
                .setWhen(System.currentTimeMillis()) // 현재 시간 표시
                .setSmallIcon(R.drawable.ic_stat_ic_notification) // 앱 아이콘이 들어감.
                .setTicker(information[0])
                .setContentTitle(information[1])
                .setContentText(information[2])
                .setContentInfo(information[3])
                .setContentIntent(pendingIntent);

        if(imagePath != null) {
            createBigPictureStyle(manager, builder, information[1], information[2], imagePath);
        } else {
            manager.notify(1, builder.build());
        }
    }

    private void createBigPictureStyle(NotificationManager manager, NotificationCompat.Builder builder, String title, String body, String imgUrl) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle(builder);
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(body);
        bigPictureStyle.setBigContentTitle(title)
                .setSummaryText(body)
                .bigPicture(bitmapFromUrl(imgUrl)); // imgUrl = 이미지 주소. ex) https://s3.ap-northeast-2.amazonaws.com/sbrain/img/no2_amgf1/no2_amgf1_033.png
        builder.setStyle(bigPictureStyle); // bulider 에 BigPictureStype을 set 해주어야 이미지가 같이 온다.
        manager.notify(1, builder.build());
    }

    private Bitmap bitmapFromUrl(String url) {
        Bitmap x;
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
            return x;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel createChannel() {
        @SuppressLint("WrongConstant")
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "ATM Notification", NotificationManager.IMPORTANCE_MAX);

        // configuration
        notificationChannel.setDescription("This is ATM_DEV Push");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 500, 1000});
        notificationChannel.enableVibration(true);
        return notificationChannel;
    }

   /*
    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }
    */

    /**
     * @param token The new token
     */
    private void sendRegistrationToServer(String token) {
        // 앱 서버가 있다면 갱신된 토큰을 다시 보낸다.
        // 파이어베이스 클라우드 스토어?

    }

}
