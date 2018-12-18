package com.mohbou.enhancedtestcivic.data.network

import com.mohbou.enhancedtestcivic.data.network.response.QuestionResponse
import com.mohbou.enhancedtestcivic.domain.Question

object Mapper {

    fun toQuestionList(questionResponseList: List<QuestionResponse>): List<Question> {
        val questionList = ArrayList<Question>(questionResponseList.size)
        questionResponseList.forEachIndexed { _, value -> questionList.add(toQuestion(value)) }
        return questionList
    }

    private fun toQuestion(questionResponse: QuestionResponse): Question {
        return Question(id=questionResponse.id)
    }

}
