package com.udacity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon

private const val NOTIFICATION_ID = 0
private const val CHANNEL_ID = "project_3"


fun NotificationManager.sendNotification(repo: Repo, downloadStatus : Boolean, applicationContext : Context ){

    val contentIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(EXTRA_NAME,repo.title)
        putExtra(EXTRA_DOWNLOAD_STATUS,downloadStatus)
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notificationChannel = NotificationChannel(CHANNEL_ID, applicationContext.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT)
    notificationChannel.enableLights(true)
    notificationChannel.lightColor = Color.GREEN
    notificationChannel.enableVibration( true)

    val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)

    val notificationAction : Notification.Action = Notification.Action.Builder(
        Icon.createWithResource(applicationContext, R.drawable.ic_assistant_black_24dp),
        applicationContext.getString(R.string.check_status),
        contentPendingIntent
    ).build()

    val notification = Notification.Builder(
        applicationContext,
        NOTIFICATION_ID.toString()
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setChannelId(CHANNEL_ID)
        .addAction(notificationAction)
        .build()

    notify(NOTIFICATION_ID, notification)
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}