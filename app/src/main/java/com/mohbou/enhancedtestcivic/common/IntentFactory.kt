package com.mohbou.enhancedtestcivic.common

import android.content.Context
import android.content.Intent
import com.google.android.exoplayer2.util.Util
import com.mohbou.enhancedtestcivic.application.QuestionApplication.Companion.applicationContext
import com.mohbou.enhancedtestcivic.features.questionDetail.activity.QuestionDetailActivity
import com.mohbou.exoplayerdemo.AudioPlayerService
import java.util.*
import javax.inject.Inject

class IntentFactory @Inject constructor(val applicationContext: Context) {
    fun intentForQuestionDetailActivity(questionID: String?): Intent {
        val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
        intent.putExtra(Constants.QUESTION_ID,questionID)
        return intent
    }

    fun intentForExoPlayer(action:Int):Intent {
        val intentPause = Intent(applicationContext, AudioPlayerService::class.java)
        intentPause.putExtra(AudioPlayerService.PLAY_PAUSE_ACTION, action)
        return intentPause

    }

}
