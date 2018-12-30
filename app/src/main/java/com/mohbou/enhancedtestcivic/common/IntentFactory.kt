package com.mohbou.enhancedtestcivic.common

import android.content.Context
import android.content.Intent
import com.mohbou.enhancedtestcivic.application.QuestionApplication.Companion.applicationContext
import com.mohbou.enhancedtestcivic.features.questionDetail.activity.QuestionDetailActivity
import java.util.*
import javax.inject.Inject

class IntentFactory @Inject constructor(val applicationContext: Context) {
    fun intentForQuestionDetailActivity(questionID: String?): Intent {
        val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
        intent.putExtra(Constants.QUESTION_ID,questionID)
        return intent
    }

}
