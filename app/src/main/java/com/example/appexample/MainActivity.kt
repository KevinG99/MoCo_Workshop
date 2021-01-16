package com.example.appexample

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //use Notification Manager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //on Click, create short Notification
        shortNotification_Button.setOnClickListener {
            val channel = createNotificationChannel(
                getString(R.string.channelID),
                getString(R.string.channelName),
                getString(R.string.short_channel_description)
            )
            notificationManager.createNotificationChannel(channel)
            //send short Notification with Notification Mananger
            notificationManager.sendshortNotification(
                shortNotification_TextField_Details.text.toString(), //text Input from Activity
                "Test Short",
                this
            )
        }

        //on Click, create expandable Notification
        expandable_Notification_Button.setOnClickListener {
            val channel = createNotificationChannel(
                getString(R.string.secChannelID),
                getString(R.string.secChannelName),
                getString(R.string.expandable_channel_descirption)
            )
            notificationManager.createNotificationChannel(channel)
            notificationManager.sendexpandableNotification(
                expandable_Notification_TextField_Details.text.toString(),
                "Test Long",
                this
            )
        }


        //TODO(Schritt 6: Klick-Event für ImageButton, um Bild aus Gallerie auszuwählen.)
        //START
        permission.setOnClickListener {
            //Prüfen, ob App Berechtigungen hat
            if (isReadStorageAllowed()) {
                // Code, wenn Berechtigung erteilt wurde.
            } else {

                //Falls keine Berechtigung vorliegt, wird diese abgefragt
                requestStoragePermission()
            }
        }
    }


    //TODO(Schritt 2 - Zunächst müssen die Berechtigungen abgefragt werden
    // um ein Bild vom Gerät auswählen zu können bzw. zu speichern.)
    //START
    /**
     * Berechtigungsanfrage
     */
    private fun requestStoragePermission() {

        /**
         * Prüft, ob eine weitere Erklärung zu der Berechtigung angegeben werden soll. Falls klar ist,
         * warum beispielsweise die Kamera bei einer Kamera-App benötigt wird, sollte diese Erklärung
         * nicht angezeigt werden. Falls zusätzlich der Standort abgefragt wird, macht es vielleicht Sinn
         * zu erklären, dass dadurch ein Bild kategorisiert werden soll etc.
         *
         * @param activity Aktuelle Activity.
         * @param permission Die Berechtigung, die abgefragt werden soll.
         * @return Ob Berechtigungsdialog/ Erklärung angezeigt werden soll.
         *
         */
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).toString()
            )
        ) {
            // Wenn der Nutzer die Berechtigung abgelehnt hat, springt der Code in diesen Block und
            // du kannst dem Nutzer erklären, weshalb du die Berechtigung benötigst etc.
            Toast.makeText(
                this, "Die App braucht die Berechtigung, um einen Hintergrund hinzuzufügen",
                Toast.LENGTH_SHORT
            ).show()
        }


        /**
         * Abfrage der Berechtigung. Diese muss im Manifest aufgeführt werden.
         */

        // Berechtigung abfragen
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            STORAGE_PERMISSION_CODE
        )
    }
    //ENDE 8.07

    //TODO(Schritt 5 - Methode erstellen, um zu prüfen, ob Berechtigungen erteilt wurden oder nicht)
    //START
    /**
     * Methode aufrufen, um Status der Berechtigung abzufragen
     */

    private fun isReadStorageAllowed(): Boolean {
        // Status der Berechtigung abfragen

        /**
         * @param permission Name der Berechtigung, die geprüft werden soll.
         *
         */
        val result = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // Wenn Berechtigungen erteilt wurden, wird true zurückgegeben, sonst false
        return result == PackageManager.PERMISSION_GRANTED
    }
    //ENDE

    //TODO(Schritt 3 - Einzigartiger Code, um Berechtigungsanfrage zuordnen zu können.)
    //START
    companion object {
        /**
         * Berechtigungscode, der in der Methode onRequestPermissionsResult abgefragt wird
         *
         * Für weitere Informationen: https://developer.android.com/training/permissions/requesting#kotlin
         */
        private const val STORAGE_PERMISSION_CODE = 1
        //ENDE
    }
}




