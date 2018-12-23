package com.mohbou.enhancedtestcivic.data.database.entities

import android.arch.persistence.room.*
import java.util.*

@Entity(
    tableName = "answer",
    indices = [Index(value = ["question_id"],unique = true)],
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("question_id")
    )]
)
data class AnswerEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val answer: String,
    @ColumnInfo(name = "question_id")
    val questionId: String
)