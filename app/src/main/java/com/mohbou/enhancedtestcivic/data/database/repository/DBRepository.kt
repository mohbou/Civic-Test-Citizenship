package com.mohbou.enhancedtestcivic.data.database.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.domain.Question

class DBRepository(private val civicTestDatabase: CivicTestDatabase) {

    fun getAllQuestions(): LiveData<List<Question>> {
        return questionDao().getQuestions()

    }

    fun getQuestionWithAnswersById(questionId:String?):LiveData<Question> {
        val questionLiveData = MutableLiveData<Question>()
        val question = questionDao().getQuestionById(questionId)
        val questionWithAnswersEntity = question.value
        questionLiveData.value=DBMapper.toQuestion(questionWithAnswersEntity!!)
        return questionLiveData
    }
    private fun questionDao(): QuestionDao {
        return civicTestDatabase.getQuestionDao()
    }

}