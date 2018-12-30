package com.mohbou.enhancedtestcivic.data.database.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.mohbou.enhancedtestcivic.common.runOnIoThread
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.dao.QuestionDao
import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.domain.Question
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableReplay.observeOn
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DBRepository(private val civicTestDatabase: CivicTestDatabase) {

    fun getAllQuestions(): LiveData<List<Question>> {
        val questions  by lazy { MutableLiveData<List<Question>>()}
        val disposable = questionDao().getQuestions()?.
               map { qs -> DBMapper.toQuestions(qs) }?.repeatUntil{
            questions.value?.isNotEmpty() ?:false
        }
            ?.subscribeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                questions.postValue(it)
            }

        return questions

    }





    private fun questionDao(): QuestionDao {


      return civicTestDatabase.getQuestionDao()


    }

}