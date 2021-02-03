package com.example.musicplayer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

private const val CHANNEL_ID = "music_player_channel"
private const val NOTIFICATION_ID = 1

class PlayerService : Service() {
    private lateinit var player: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager


    private fun prepareSongs(player: SimpleExoPlayer) {
        for (song in Songs.sampleTracks) {
            val item = MediaItem.fromUri(song.uri)
                .buildUpon()
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(song.title)
                        .build())
                .build()
            player.addMediaItem(item)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context = this
        player = SimpleExoPlayer.Builder(context).build()
        prepareSongs(player)
        player.prepare()

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
            this@PlayerService,
            CHANNEL_ID,
            R.string.channel_name,
            R.string.channel_desc,
            NOTIFICATION_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return PendingIntent.getActivity(
                        this@PlayerService,
                        0,
                        Intent(this@PlayerService, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return Songs.sampleTracks[player.currentWindowIndex].title
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return null
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return null
                }
            },
            object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: Notification,
                    ongoing: Boolean
                ) {
                    if (ongoing)
                        startForeground(notificationId, notification)
                    else
                        stopForeground(false)
                }

                override fun onNotificationCancelled(
                    notificationId: Int,
                    dismissedByUser: Boolean
                ) {
                    stopSelf()
                }
            }
        )
        playerNotificationManager.setPlayer(player)

    }

    inner class PlayerServiceBinder : Binder() {
        val service
            get() = this@PlayerService

        val player
            get() = this@PlayerService.player
    }

    override fun onBind(intent: Intent?): IBinder? {
        return PlayerServiceBinder()
    }

    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        player.release()
        super.onDestroy()
    }


}