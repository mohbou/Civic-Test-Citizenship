package com.mohbou.enhancedtestcivic.data.database.utils

import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.domain.Question
import java.util.*

object DBMapper {

   fun toEntityQuestion(question: Question): QuestionEntity {
      return QuestionEntity(question.id, question.question!!, question.review ?:false)
    }

    private fun toAnswerEntity(answer: Answer,questionId:String): AnswerEntity {
        return AnswerEntity(id= UUID.randomUUID().toString(),answer = answer.answer, questionId = questionId)
    }

    fun toEntityQuestionList(questions: List<Question>): List<QuestionEntity> {
       return questions.map(this::toEntityQuestion).toList()

    }

   private fun toQuestion(question:QuestionEntity):Question {
        return Question(question.id,question.question,review = question.review)
    }

    fun toQuestions(questions:List<QuestionEntity>):List<Question> {
        return questions.map { it -> toQuestion(it) }.toList()
    }

    private fun toAnswerEntityList(answers: Question): List<AnswerEntity> {
        return answers.answers!!.map{it-> toAnswerEntity(it,answers.id)}.toList()
    }

    fun toAnswersEntityList(questions:List<Question>):List<AnswerEntity> {
       return questions.flatMap {
         question ->  toAnswerEntityList(question)}.toList()
    }

    fun toQuestion(questionEntity: QuestionWithAnswersEntity):Question {

        return Question(questionEntity.question.id,questionEntity.question.question,toAnswerList(questionEntity.answers),questionEntity.question.review)

    }

    fun toQuestionList(questions:List<QuestionWithAnswersEntity>):List<Question> {
        return questions.map (this::toQuestion).toList()
    }

    private fun toAnswerList(answersEntities: List<AnswerEntity>): List<Answer>? {
        return answersEntities.map(this::toAnswer).toList()
    }

    private fun toAnswer(answerEntity: AnswerEntity): Answer {
        return Answer(answerEntity.id,answerEntity.answer)
    }
}