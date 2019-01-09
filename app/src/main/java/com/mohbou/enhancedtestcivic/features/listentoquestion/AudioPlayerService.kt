package com.mohbou.exoplayerdemo

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.features.home.activity.MainActivity

class AudioPlayerService : Service() {


    companion object {
        const val VIDEO_FILE_ID = "VideoFileID"
        const val PLAY_PAUSE_ACTION = "playPauseAction"
        const val NOTIFICAITON_ID = 0
    }

    private var player: SimpleExoPlayer? = null

    private val PLAYBACK_CHANNEL_ID = "playback_channel"
    private val PLAYBACK_NOTIFICATION_ID = 1
    private val MEDIA_SESSION_TAG = "audio_demo"
    private val DOWNLOAD_CHANNEL_ID = "download_channel"
    private val DOWNLOAD_NOTIFICATION_ID = 2
    private val BASE_URL="https://www.uscis.gov/sites/default/files/files/nativedocuments/"

    var playerNotificationManager:PlayerNotificationManager?=null


    override fun onCreate() {
        super.onCreate()
        val context: Context = this
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
        val dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        val cacheDataSourceFactory = CacheDataSourceFactory(DownloadUtil.getCache(context), dataSourceFactory,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)


        val concatenatingMediaSource = ConcatenatingMediaSource()

        for (index in 1..100) {
            val fileName = if (index <= 9)
                "Track 0$index.mp3"
            else "Track $index.mp3"

            val mediaSource = ExtractorMediaSource.Factory(cacheDataSourceFactory).createMediaSource(Uri.parse(BASE_URL+fileName))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }



        player?.prepare(concatenatingMediaSource)
        player?.playWhenReady = true

         playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context,
            PLAYBACK_CHANNEL_ID,
            R.string.playback_channel_name,
            PLAYBACK_NOTIFICATION_ID,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun createCurrentContentIntent(player: Player?): PendingIntent? {
                    val intent = Intent(context, MainActivity::class.java)
                    return PendingIntent.getActivity(
                        context, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                override fun getCurrentContentText(player: Player?): String? {
                    return null //Samples.SAMPLES[player!!.currentWindowIndex].description
                }

                override fun getCurrentContentTitle(player: Player?): String {
                    return "Sample" //Samples.SAMPLES[player!!.currentWindowIndex].title
                }

                override fun getCurrentLargeIcon(
                    player: Player?,
                    callback: PlayerNotificationManager.BitmapCallback?
                ): Bitmap? {
                    return null //Samples.getBitmap(context,Samples.SAMPLES[player!!.currentWindowIndex].bitmapResource)
                }
            })

        playerNotificationManager!!.run {
            setNotificationListener(object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationCancelled(notificationId: Int) {
                   stopSelf()
                }

                override fun onNotificationStarted(notificationId: Int, notification: Notification?) {
                    startForeground(notificationId,notification)
                }
            })

            playerNotificationManager!!.setPlayer(player)
        }
    }

    override fun onDestroy() {
        playerNotificationManager!!.setPlayer(null)
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val action = it.getIntExtra(AudioPlayerService.PLAY_PAUSE_ACTION, -1)
            when (action) {
                0 -> player!!.playWhenReady = false
                1 -> player!!.playWhenReady =true
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class AudioServiceBinder:Binder() {
        fun getPlayer() = player
    }

}