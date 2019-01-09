package com.mohbou.exoplayerdemo

import android.app.Notification
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.offline.DownloadManager.TaskState
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.scheduler.Scheduler
import com.google.android.exoplayer2.ui.DownloadNotificationUtil
import com.mohbou.enhancedtestcivic.R


class AudioDownloadService : DownloadService(
    2,
    DownloadService.DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    "download_channel",
    R.string.download_channel_name
) {

    override fun getDownloadManager(): DownloadManager {
        return DownloadUtil.getDownloadManager(this)
    }

    override fun getScheduler(): Scheduler? {
        return null
    }

    override fun getForegroundNotification(taskStates: Array<TaskState>?): Notification {
        return DownloadNotificationUtil.buildProgressNotification(
            this,
            R.drawable.exo_icon_play,
            "download_channel", null, null,
            taskStates!!
        )
    }

}