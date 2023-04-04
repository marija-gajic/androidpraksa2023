package com.example.feedcraft.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.feedcraft.activityMain.MainActivity
import com.example.feedcraft.R
import com.example.feedcraft.data.UIApplication

class NotificationHelper (val context: Context) {
    var CHANNEL_ID = "channel_id"
    val NOTIFICATION_ID = 1
    lateinit  var notificationPhoto: Bitmap


    fun createNotification(title: String, message: String){

        if (UIApplication.addPhotoFlag == "checked") {
            notificationPhoto = UIApplication.tempEditedPhoto!!
            UIApplication.addPhotoFlag = ""
        } else {
            notificationPhoto = BitmapFactory.decodeResource(context.resources,
                R.drawable.ic_launcher
            )
        }

        createNotificationChannel()
        val intent = Intent(context, MainActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)
        //val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
        //notificationPhoto = UIApplication.tempEditedPhoto!!
        val icon = notificationPhoto

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager =  context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}