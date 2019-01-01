package com.mohbou.enhancedtestcivic.features.questionDetail.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohbou.enhancedtestcivic.R
import com.mohbou.enhancedtestcivic.common.Constants
import com.mohbou.enhancedtestcivic.features.questionDetail.fragment.QuestionDetailPagerFragment

class QuestionDetailActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        val questionId = intent.getSerializableExtra(Constants.QUESTION_ID) as String
        val fragment = QuestionDetailPagerFragment.newInstance(questionId)

        supportFragmentManager.beginTransaction()
                              .add(R.id.questionDetailContainer,fragment)
                              .commit()
    }

}
