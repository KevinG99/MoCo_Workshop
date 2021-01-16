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
        Intent(context, MainActivity2::class.java)          //create Intent, to MainActivity2

    val contentPendingIntent =
        PendingIntent.getActivity(                             //erzeugt Pending Intent
            context,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.channelID)
    )
        .setSmallIcon(R.drawable.ic_short_notification)        //muss gesetzt sein
        .setContentTitle(title)                                //muss gesetzt sein
        .setContentText(messagebody)                           //muss gesetzt sein
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)                //ueberlaesst der fremden application mein gesetztes Intent --> Main Activity
        .setAutoCancel(true)                                   //entfernt die Notification, beim Klick


    notify(                                                    //automatisches updaten der letzten Benachrichtigung
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
    val contentIntent =
        Intent(context, MainActivity::class.java)                        //Intent erzeugt, zur MainActivity

    //TODO: Step 1.2 create PendingIntent
    val contentPendingIntent =
        PendingIntent.getActivity(                                       //erzeugt Pending Intent
            context,
            NOTIFICATION_ID_0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

                                                                             //set Alarm Receiver and create a pending Intent
    //TODO: Step 1.3 add sendNew Action (GIVEN)
    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        R.string.secChannelID,
        alarmIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    //TODO: Step 1.4 get an instance of NotificationCompat.Builder
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.secChannelID)
    )
        //TODO: Step 1.5 set title, text and icon to builder
        .setContentTitle(title)
        .setContentText(messagebody)
        .setSmallIcon(R.drawable.ic_expandable_notification)

        //TODO: Step 1.6 set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)

        //TODO: Step 1.7 set content Intent
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

        //TODO: Step 1.8 add color style
        .setColorized(true)                                      //enable background color if the activity is in a foreground
        .setColor(rgb)                                           //setze Farbe fuer Icon und Buttons

        //TODO: Step 1.9 add style to builder
        .setStyle(
            NotificationCompat.BigTextStyle()                    //laesst den Text im Screen erscheinen und zeigt einen
        )                                                       //groesseren message Body an

        //TODO: Step 1.10 add Action to send a new Notification
        .addAction(
            R.drawable.ic_expandable_notification_32,
            "send new",
            pendingIntent
        )

    //TODO: Step 1.11 call notify
    notify(NOTIFICATION_ID_1, builder.build())
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        description = channelDescription
        enableVibration(true)
        enableLights(true)
        canBubble()                                                       //lets the notification peeks into the Screen
        }
    }
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}