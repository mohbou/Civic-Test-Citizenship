package com.mohbou.enhancedtestcivic.data.database.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class QuestionWithAnswersEntity {
    @Embedded
    lateinit var questionEntity: QuestionEntity
    @Relation(parentColumn = "id", entityColumn = "question_id", entity = AnswerEntity::class)
    lateinit var answersEntities: List<AnswerEntity>
}