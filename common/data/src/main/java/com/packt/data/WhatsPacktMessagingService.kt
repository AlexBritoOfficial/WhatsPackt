package com.packt.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.packt.framework.navigation.DeepLinks

class WhatsPacktMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "Chat_message"
        const val CHANNEL_DESCRIPTION = "Receive a notification when a new chat is received"
        const val CHANNEL_TITLE = "New chat message notification"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {

            // We can extract the data (sender, message content, or chat id) from the remote message received
            val senderName = remoteMessage.data["senderName"]
            val messageContent = remoteMessage.data["message"]
            val chatId = remoteMessage.data["chatId"]
            val messageId = remoteMessage.data["messageId"]

            // Create and show a notification for the new received message
            if (chatId != null && messageId != null){
                showNotification(senderName, messageId, messageContent, chatId)
            }
        }
    }

    private fun showNotification(senderName: String?, messageId: String, messageContent: String?, chatId: String){

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel
        // To support versions lower than Android Oreo, check the API level here

        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_DESCRIPTION, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = CHANNEL_TITLE
        }

        notificationManager.createNotificationChannel(notificationChannel)

        // Create an Intent that will be responsible for opening the chat when the notification is clicked.

        val deepLinkUrl = DeepLinks.chatRoute.replace("{chatId}", chatId)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create a PendingIntent to launch the activity when the notification is clicked.

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification and display it.
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(senderName)
            .setContentText(messageContent)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Show the notification
        notificationManager.notify(messageId.toInt(), notification)
    }
}