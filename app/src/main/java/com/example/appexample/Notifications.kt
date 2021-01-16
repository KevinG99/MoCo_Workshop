package com.example.appexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

private const val NOTIFICATION_ID_0 = 0
private const val NOTIFICATION_ID_1 = 1

/*Benachrichtigung, die nur in der Benachrichtigungsleiste angezeigt wird*/
fun NotificationManager.sendshortNotification(
    messagebody: String, title: String, context: Context
) {
    val contentIntent =
        Intent(context, MainActivity2::class.java) //Intent erzeugt, zur MainActivity2
    val contentPendingIntent =
        PendingIntent.getActivity(                       //erzeugt Pending Intent
            context,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.channelID)
    )
        .setSmallIcon(R.drawable.ic_short_notification)     //muss gesetzt sein
        .setContentTitle(title)                             //muss gesetzt sein
        .setContentText(messagebody)                        //muss gesetzt sein
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)         //ueberlaesst der fremden application mein gesetztes Intent --> Main Activity
        .setAutoCancel(true)                            //entfernt die Notification, beim Klick

    //automatisches updaten der letzten Benachrichtigung
    notify(
        NOTIFICATION_ID_0,
        builder.build()
    )
}

/*Benachrichtigung, die laengeren Text anzeigen laesst und ausserdem noch im Screen angezeigt wird*/
fun NotificationManager.sendexpandableNotification(
    messagebody: String, title: String, context: Context
) {

    val rgb = ContextCompat.getColor(context, R.color.purple_500)

    val contentIntent =
        Intent(context, MainActivity::class.java) //Intent erzeugt, zur MainActivity
    val contentPendingIntent =
        PendingIntent.getActivity(                       //erzeugt Pending Intent
            context,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    //set Alarm Receiver and create a pending Intent
    //TODO: set Intents
    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        R.string.secChannelID,
        alarmIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    //creates the Notification
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.secChannelID)
    )
        .setContentTitle(title)
        .setContentText(messagebody)
        .setSmallIcon(R.drawable.ic_expandable_notification)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setColorized(true)                             //enable background color if the activity is in a foreground
        .setColor(rgb)                                  //setze Farbe fuer Icon und Buttons
        .setStyle(
            NotificationCompat.BigTextStyle()           //laesst den Text im Screen erscheinen und zeigt einen
        )                                               //groesseren message Body an
        .addAction(
            R.drawable.ic_expandable_notification_32,
            "snooze",
            pendingIntent
        )
    //build the notification
    notify(NOTIFICATION_ID_1, builder.build())
}


/*erstelle Benachrichtigungskanaele, diese sind seit SDK Version 26 Pflicht*/
fun createNotificationChannel(channelID: String, channelName: String, channelDescription: String) {
    NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
        description = channelDescription
        enableVibration(true)
        enableLights(true)
    }
}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}