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
        val answerList = ArrayList<Answer>(questionResponse.answerResponse?.size ?:0)
        questionResponse.answerResponse?.forEachIndexed { _, answerResponse ->
            answerList.add(Answer(answer=answerResponse.answer)) }

        return Question(question = questionResponse.question,answers = answerList)
    }

}
