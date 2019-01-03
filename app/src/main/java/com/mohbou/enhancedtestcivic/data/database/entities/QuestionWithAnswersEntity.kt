package com.mohbou.enhancedtestcivic.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class QuestionWithAnswersEntity {
    @Embedded
    lateinit var question: QuestionEntity
    @Relation(parentColumn = "id", entityColumn = "question_id", entity = AnswerEntity::class)
    lateinit var answers: List<AnswerEntity>
}