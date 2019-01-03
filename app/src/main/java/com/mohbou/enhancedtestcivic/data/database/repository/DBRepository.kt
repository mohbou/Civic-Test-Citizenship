package com.mohbou.enhancedtestcivic.data.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.domain.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBRepository(private val civicTestDatabase: CivicTestDatabase) {

    fun getAllQuestions(): LiveData<List<Question>> {
        return questionDao().getQuestions()

    }

    suspend fun getQuestionWithAnswersById(questionId: String?): LiveData<Question> {
        val questionLiveData = MutableLiveData<Question>()
        withContext(Dispatchers.IO) {

            val question = questionDao().getQuestionById(questionId)

            question.let {
                questionLiveData.postValue(DBMapper.toQuestion(it))
            }

            return@withContext questionLiveData

        }
        return questionLiveData
    }

    suspend fun updateQuestionReview(question:Question) {
        withContext(Dispatchers.IO) {
            questionDao().updateQuestion(DBMapper.toEntityQuestion(question))
        }
    }

    private fun questionDao(): QuestionDao {
        return civicTestDatabase.getQuestionDao()
    }

}