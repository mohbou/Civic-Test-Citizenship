package com.mohbou.enhancedtestcivic.data.database.entities

import androidx.room.*

@Entity(
    tableName = "answer",
    indices = [Index(value = ["question_id"])],
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("question_id")
    )]
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val answer: String,
    @ColumnInfo(name = "question_id")
    val questionId: String
)