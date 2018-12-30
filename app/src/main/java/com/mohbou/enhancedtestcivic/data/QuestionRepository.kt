package com.mohbou.enhancedtestcivic.data

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.repository.DBRepository
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.domain.QuestionStub
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class QuestionRepository @Inject constructor(val networkRepository: NetworkRepository,val dbRepository: DBRepository) {

    fun getAllQuestions(): LiveData<List<Question>> {
        return dbRepository.getAllQuestions()
    }



    fun getQuestionById(questionId: UUID?): LiveData<Question> {
        val questionLiveData = MutableLiveData<Question>()



        return questionLiveData
    }


}