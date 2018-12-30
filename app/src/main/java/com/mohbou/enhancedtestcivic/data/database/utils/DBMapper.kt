package com.mohbou.enhancedtestcivic.data.database.utils

import com.mohbou.enhancedtestcivic.data.database.entities.AnswerEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionEntity
import com.mohbou.enhancedtestcivic.data.database.entities.QuestionWithAnswersEntity
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.domain.Question
import java.util.*

object DBMapper {

   private fun toEntityQuestion(question: Question): QuestionEntity {
      return QuestionEntity(question.id.toString(), question.question!!, false)
    }

    private fun toAnswerEntity(answer: Answer,questionId:String): AnswerEntity {
        return AnswerEntity(answer.id.toString(), answer.answer, questionId)
    }

    fun toEntityQuestionList(questions: List<Question>): List<QuestionEntity> {
       return questions.map(this::toEntityQuestion).toList()

    }

   private fun toQuestion(question:QuestionEntity):Question {
        return Question(UUID.fromString(question.id),question.question,review = question.review)
    }

    fun toQuestions(questions:List<QuestionEntity>):List<Question> {
        return questions.map { it -> toQuestion(it) }.toList()
    }

    private fun toAnswerEntityList(answers: Question): List<AnswerEntity> {
        return answers.answers!!.map{it-> toAnswerEntity(it,answers.id.toString())}.toList()
    }

    fun toAnswersEntityList(questions:List<Question>):List<AnswerEntity> {
       return questions.flatMap (this::toAnswerEntityList).toList()
    }

    private fun toQuestion(question: QuestionWithAnswersEntity):Question {

        return Question(UUID.fromString(question.questionEntity.id),question.questionEntity.question,toAnswerList(question.answersEntities),question.questionEntity.review)

    }

    fun toQuestionList(questions:List<QuestionWithAnswersEntity>):List<Question> {
        return questions.map (this::toQuestion).toList()
    }

    private fun toAnswerList(answersEntities: List<AnswerEntity>): List<Answer>? {
        return answersEntities.map(this::toAnswer).toList()
    }

    private fun toAnswer(answerEntity: AnswerEntity): Answer {
        return Answer(UUID.fromString(answerEntity.id),answerEntity.answer)
    }
}