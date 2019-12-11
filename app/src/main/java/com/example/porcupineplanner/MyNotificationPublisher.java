/**
 * This program assists MainActivity in sending notifications to the user. It functions as a reciever for
 *      any notifications that are saved on the channel.
 * CPSC 312-01, Fall 2019
 * Final Project - Porcupine Planner
 * Sources to Cite:
 *      Working with notifications with the help of these tutorial/discussion boards:
 *          https://developer.android.com/training/notify-user/build-notification
 *          https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
 *
 * @author Kellie Colson and Elizabeth Larson
 * @version v1.0 12/11/19
 */

package com.example.porcupineplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyNotificationPublisher extends BroadcastReceiver {
    // Fields:
    //      NOTIFICATION_ID - name of the notification_id storage
    //      NOTIFICATION - name of the notification
    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    /**
     * Receives the intent that has notification information
     * See sources
     *
     * @param context the Context in which the notification is being displayed
     * @param intent Intent object with notification information
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }
}