package com.mohbou.enhancedtestcivic.data

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.mohbou.enhancedtestcivic.data.database.repository.DBRepository
import com.mohbou.enhancedtestcivic.data.network.NetworkRepository
import com.mohbou.enhancedtestcivic.domain.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class QuestionRepository @Inject constructor(val networkRepository: NetworkRepository,val dbRepository: DBRepository) {
    @SuppressLint("CheckResult")
    fun getAllQuestions(): LiveData<List<Question>> {
        val questionListLiveData = MutableLiveData<List<Question>>()


        dbRepository.getNumberOfRows()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe { rows ->  Log.d("numberofrows",rows.toString()) }

        networkRepository.getAllQuestions()?.subscribe {
            if(it.success && it.data?.size!!>0) {
                questionListLiveData.value=it.data
            }
           }

        return questionListLiveData
    }

    // this method will help to get the index of a question and set the current Item in viewpager accordingly

    fun getIndexQuestionById(questionId: UUID): LiveData<Int> {
        val index= MutableLiveData<Int>()
        getAllQuestions().value?.forEachIndexed { tempIndex, question ->
            if (question.id==questionId) {
                index.value = tempIndex


            }
        }
        return index
    }

    fun getQuestionById(questionId: UUID?): LiveData<Question> {
        val questionLiveData = MutableLiveData<Question>()

        networkRepository.getQuestionById(questionId)

        return questionLiveData
    }


}