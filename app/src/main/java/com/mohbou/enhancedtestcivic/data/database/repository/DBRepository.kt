package com.mohbou.enhancedtestcivic.data.database.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.domain.Question
import io.reactivex.Single

class DBRepository(private val civicTestDatabase: CivicTestDatabase) {

    fun getAllQuestions(): LiveData<List<Question>> {

        val questionList = DBMapper.toQuestionList(questionDao().getAllQuestions().value!!)
        val questions= MutableLiveData<List<Question>>()
        questions.value=questionList

        return questions
    }

    fun getNumberOfRows(): Single<Int>? = civicTestDatabase.getQuestionDao().getNumberOfRows()

    fun addQuestions(questions: List<Question>) {
        questionDao().addAllQuestions(DBMapper.toEntityQuestionList(questions))
        questionDao().addAllAnswers(DBMapper.toAnswersEntityList(questions))

    }

    private fun questionDao(): QuestionDao {
      return civicTestDatabase.getQuestionDao()
    }

}