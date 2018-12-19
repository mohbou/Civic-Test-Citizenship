package com.mohbou.enhancedtestcivic.data

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.domain.Question
import java.util.*
import javax.inject.Inject

class QuestionRepository @Inject constructor(val networkRepository: NetworkRepository) {
    @SuppressLint("CheckResult")
    fun getAllQuestions(): LiveData<List<Question>> {
        val questionListLiveData = MutableLiveData<List<Question>>()
        networkRepository.getAllQuestions()?.subscribe {
            if(it.success && it.data?.size!!>0) {
                questionListLiveData.value=it.data
            }

        }

        return questionListLiveData
    }

    fun getQuestionQuestionById(questionId:UUID):Question {
        return Question()
    }
}