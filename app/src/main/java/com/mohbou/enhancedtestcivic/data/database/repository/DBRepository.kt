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
        val questions= MutableLiveData<List<Question>>()
        questions.postValue(DBMapper.toQuestionList(questionDao().getAllQuestions()!!))



//  questionDao().getAllQuestions()?.subscribeOn(Schedulers.computation())
//      ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe {
//      it ->  Log.d("numberofrow","enters here in db")
//
//
// }

        return questions

    }

    fun getNumberOfRows(): Single<Int>? = civicTestDatabase.getQuestionDao().getNumberOfRows()

    fun addQuestions(questions: List<Question>) {
        GlobalScope.launch{

            questionDao().addAllQuestions(DBMapper.toEntityQuestionList(questions))
            questionDao().addAllAnswers(DBMapper.toAnswersEntityList(questions))



        }

    }

    private fun questionDao(): QuestionDao {
      return civicTestDatabase.getQuestionDao()
    }

}