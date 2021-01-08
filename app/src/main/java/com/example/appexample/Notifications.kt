package com.example.appexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID_0 = 0
private const val NOTIFICATION_ID_1 = 1

/*Benachrichtigung, die nur in der Benachrichtigungsleiste angezeigt wird*/
fun NotificationManager.sendshortNotification(
    messagebody: String, title: String, applicationContext: Context
) {
    val contentIntent =
        Intent(applicationContext, MainActivity2::class.java) //Intent erzeugt, zur MainActivity
    val contentPendingIntent =
        PendingIntent.getActivity(                       //erzeugt Pending Intent
            applicationContext,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channelID)
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
    messagebody: String, title: String, applicationContext: Context
) {

    val rgb = applicationContext.resources.getColor(R.color.purple_500) //Farbe aus colors.xml

    val contentIntent =
        Intent(applicationContext, MainActivity::class.java) //Intent erzeugt, zur MainActivity
    val contentPendingIntent =
        PendingIntent.getActivity(                       //erzeugt Pending Intent
            applicationContext,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    //set Alarm Receiver and create a pending Intent
    //TODO: set Intents
    val alarmIntent = Intent(applicationContext, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        R.string.secChannelID,
        alarmIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    //creates the Notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.secChannelID)
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
    val notificationChannel =
        NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_NONE)

    notificationChannel.description = channelDescription
    notificationChannel.enableVibration(true)
}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}