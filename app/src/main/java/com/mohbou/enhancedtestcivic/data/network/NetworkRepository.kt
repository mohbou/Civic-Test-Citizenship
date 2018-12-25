package com.mohbou.enhancedtestcivic.data.network

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.mohbou.enhancedtestcivic.data.network.response.MyJson
import com.mohbou.enhancedtestcivic.data.network.response.QuestionResponse
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.domain.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import java.io.InputStream
import javax.inject.Inject

/*
*NetworkRepository on this scenario is reading from a JSON file, we will improve it once we have an alternative
* Rest calls?
 */
class NetworkRepository @Inject constructor(val inputStream: InputStream, var gson: Gson) {


    @SuppressLint("CheckResult")
    fun getAllQuestions(): Observable<Result<List<Question>>>? {
        val allQuestionsSubject = SingleSubject.create<Result<List<Question>>>()

        Observable.fromCallable { getQuestions() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    allQuestionsSubject.onSuccess(Result.fromData(Mapper.toQuestionList(it)))
                },
                {
                    allQuestionsSubject.onSuccess(Result.fromError(it))
                })
        return allQuestionsSubject.toObservable()
    }


/*
* read from JSON file and populate question objects
*
* */

    private fun getQuestions(): List<QuestionResponse> {

        val questions: MutableList<QuestionResponse>? = mutableListOf()

        val json = inputStream
            .readBytes()
            .toString(Charsets.UTF_8)

        val myTests = gson.fromJson<MyJson>(json, MyJson::class.java)

        myTests.test.forEach { sections ->
            sections.section_content
                .forEach { subSection ->
                    questions?.addAll(subSection.questionResponse)

                }

        }


        return questions!!.toList()

    }


}
