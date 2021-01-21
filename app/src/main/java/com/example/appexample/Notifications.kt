package com.example.appexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

private const val NOTIFICATION_ID_0 = 0
private const val NOTIFICATION_ID_1 = 1

/*Benachrichtigung, die nur in der Benachrichtigungsleiste angezeigt wird*/
fun NotificationManager.sendshortNotification(
    messagebody: String, title: String, context: Context
) {
    val contentIntent =
        Intent(context, MainActivity2::class.java)

    val contentPendingIntent =
        PendingIntent.getActivity(
            context,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.channelID)
    )
        .setSmallIcon(R.drawable.ic_short_notification)
        .setContentTitle(title)
        .setContentText(messagebody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)


    notify(
        NOTIFICATION_ID_0,
        builder.build()
    )
}

/*Benachrichtigung, die laengeren Text anzeigen laesst und ausserdem noch im Screen angezeigt wird*/
//TODO: Step 1.0 extension function to send messages (GIVEN)
fun NotificationManager.sendexpandableNotification(
    messagebody: String, title: String, context: Context
) {

    val rgb = ContextCompat.getColor(context, R.color.purple_500)

    //TODO: Step 1.1 create Intent

    //TODO: Step 1.2 create PendingIntent

    //TODO: Step 1.3 add sendNew Action (GIVEN)

    //TODO: Step 1.4 get an instance of NotificationCompat.Builder

        //TODO: Step 1.5 set title, text and icon to builder

        //TODO: Step 1.6 set priority

        //TODO: Step 1.7 set content Intent

        //TODO: Step 1.8 add color style

        //TODO: Step 1.9 add style to builder

        //TODO: Step 1.10 add Action to send a new Notification


    //TODO: Step 1.11 call notify
}


/*erstelle Benachrichtigungskanaele, diese sind seit SDK Version 26 Pflicht*/
//TODO: Step 2.0 global function to create a Notification Channel (GIVEN)
fun createNotificationChannel(
    channelID: String,
    channelName: String,
    channelDescription: String
): NotificationChannel {
    return NotificationChannel(
        channelID,
        channelName,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
            description = channelDescription                //set channel description
            enableVibration(true)                 //enable vibration
            enableLights(true)                      //enable the notification LED
    }
}

//TODO: Step 3.0 extension function to cancel Notfications (GIVEN)
fun NotificationManager.cancelNotifications() {
    cancelAll()
}