package com.example.appexample

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //on Click, create short Notification
        shortNotification_Button.setOnClickListener {
            createNotificationChannel(
                getString(R.string.channelID),
                getString(R.string.channelName),
                getString(R.string.short_channel_description)
            )
            //use Notification Manager
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //send short Notification with Notification Mananger
            notificationManager.sendshortNotification(
                shortNotification_TextField_Details.text.toString(), //text Input from Activity
                "Test Short",
                this
            )
        }

        //on Click, create expandable Notification
        expandable_Notification_Button.setOnClickListener {
            createNotificationChannel(
                getString(R.string.secChannelID),
                getString(R.string.secChannelName),
                getString(R.string.expandable_channel_descirption)
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.sendexpandableNotification(
                expandable_Notification_TextField_Details.text.toString(),
                "Test Long",
                this
            )
        }
    }




}



