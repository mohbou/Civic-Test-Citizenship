package com.mohbou.enhancedtestcivic.data.network

import com.mohbou.enhancedtestcivic.data.network.response.QuestionResponse
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.domain.Question

object Mapper {

    fun toQuestionList(questionResponseList: List<QuestionResponse>): List<Question> {
        val questionList = ArrayList<Question>(questionResponseList.size)
        questionResponseList.forEachIndexed { _, value -> questionList.add(toQuestion(value)) }
        return questionList
    }

    private fun toQuestion(questionResponse: QuestionResponse): Question {
        val answerList = ArrayList<Answer>(questionResponse.answers?.size ?:0)
        questionResponse.answers?.forEachIndexed { _, answerResponse ->
            answerList.add(Answer(answerResponse.id,answerResponse.answer)) }

        return Question(id=questionResponse.id,question = questionResponse.question,answers = answerList)
    }

}
