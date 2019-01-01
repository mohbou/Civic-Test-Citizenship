package com.mohbou.enhancedtestcivic.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.mohbou.enhancedtestcivic.domain.Answer
import com.mohbou.enhancedtestcivic.domain.Question

class QuestionWithAnswersEntity {
    @Embedded
    lateinit var question: Question
    @Relation(parentColumn = "id", entityColumn = "question_id", entity = AnswerEntity::class)
    lateinit var answers: List<Answer>
}