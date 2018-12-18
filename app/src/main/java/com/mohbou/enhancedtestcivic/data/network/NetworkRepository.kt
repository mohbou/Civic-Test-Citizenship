package com.mohbou.enhancedtestcivic.data.network

import com.mohbou.enhancedtestcivic.data.network.response.AnswerResponse
import com.mohbou.enhancedtestcivic.data.network.response.QuestionResponse
import com.mohbou.enhancedtestcivic.domain.Question
import com.mohbou.enhancedtestcivic.domain.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import org.json.JSONObject
import java.io.InputStream


class NetworkRepository {


    fun getAllQuestions(): Observable<Result<List<Question>>>? {
        val allQuestionsSubject = SingleSubject.create<Result<List<Question>>>()

        getQuestions()?.subscribeOn(Schedulers.io())
                      ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                {
                    allQuestionsSubject.onSuccess(Result.fromData(Mapper.toQuestionList(it)))
                },
                {
                    allQuestionsSubject.onSuccess(Result.fromError(it))
                })
        return allQuestionsSubject.toObservable()
    }

    private fun getQuestions(): Observable<List<QuestionResponse>>? {
        val source:InputStream? = null
        val questions:MutableList<QuestionResponse>?= mutableListOf()

        val inputStream = source
        val json = inputStream
            ?.readBytes()
            ?.toString(Charsets.UTF_8)


        val jsonBody = JSONObject(json)

        val jsonArray = jsonBody.getJSONArray("test")

        for (i in 0 until jsonArray.length()) {
            val jsonSection = jsonArray.getJSONObject(i)
            val sectionName = jsonSection.getString("section_name")
            val subSection = jsonSection.getJSONArray("section_content")
            for (j in 0 until subSection.length()) {
                val jsonSubSection = subSection.getJSONObject(j)
                val subSectionName = jsonSubSection.getString("sub_section_name")
                val subSections = jsonSubSection.getJSONArray("sub_section")
                for (x in 0 until subSections.length()) {
                    val jsonQuestion = subSections.getJSONObject(x)
                    val questionStatement = jsonQuestion.getString("question")
                    val question=
                        QuestionResponse(question = questionStatement)

                    val jsonAnswers = jsonQuestion.getJSONArray("Answer")
                    val answers = mutableListOf<AnswerResponse>()
                    for (v in 0 until jsonAnswers.length()) {
                        val jsonAnswer = jsonAnswers.getJSONObject(v)
                        val answerStatement = jsonAnswer.getString("answer")
                        val answer = AnswerResponse(answer = answerStatement)
                        answers.add(answer)

                    }
                    question.answers =answers
                    questions?.add(question)
                }

            }
        }

        return Observable.just(questions)

    }


}
