package com.mohbou.enhancedtestcivic.data

import androidx.lifecycle.LiveData
import com.mohbou.enhancedtestcivic.data.database.repository.DBRepository
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.domain.Question
import javax.inject.Inject

class QuestionRepository @Inject constructor(val networkRepository: NetworkRepository,val dbRepository: DBRepository) {

    fun getAllQuestions(): LiveData<List<Question>> {
        return dbRepository.getAllQuestions()
    }

    fun getAllQuestionstoReview(): LiveData<List<Question>> {
        return dbRepository.getAllQuestionsToReview()
    }

    suspend fun getQuestionById(questionId: String?): LiveData<Question> {
        return dbRepository.getQuestionWithAnswersById(questionId)
    }

   suspend fun updateQuestionReview(question:Question) {
        dbRepository.updateQuestionReview(question)
    }




}