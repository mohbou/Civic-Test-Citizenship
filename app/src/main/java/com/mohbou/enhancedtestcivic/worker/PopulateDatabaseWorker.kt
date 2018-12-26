package com.mohbou.enhancedtestcivic.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.mohbou.enhancedtestcivic.data.database.CivicTestDatabase
import com.mohbou.enhancedtestcivic.data.database.utils.DBMapper
import com.mohbou.enhancedtestcivic.domain.MyJson
import com.mohbou.enhancedtestcivic.domain.Question
import javax.inject.Inject

class PopulateDatabaseWorker   constructor(val context: Context,workerParams:WorkerParameters): Worker(context,workerParams) {


    var civicTestDatabase: CivicTestDatabase =CivicTestDatabase.getInstance(context)

    override fun doWork(): Result {

        val questions = getQuestions()

        civicTestDatabase.getQuestionDao().addAllQuestions(
            DBMapper.toEntityQuestionList(questions)
        )
        civicTestDatabase.getQuestionDao()
            .addAllAnswers(DBMapper.toAnswersEntityList(questions))

        return Result.success()


    }

    private fun getQuestions(): List<Question> {

        val questions: MutableList<Question>? = mutableListOf()

        val json = applicationContext.resources.openRawResource(
            applicationContext.resources.getIdentifier(
                "test_civic_question_version2",
                "raw",
                applicationContext.packageName
            )
        )
            .readBytes()
            .toString(Charsets.UTF_8)

        val myTests = Gson().fromJson<MyJson>(json, MyJson::class.java)

        myTests.test.forEach { sections ->
            sections.section_content
                .forEach { subSection ->
                    questions?.addAll(subSection.question)

                }

        }


        return questions!!.toList()

    }
}