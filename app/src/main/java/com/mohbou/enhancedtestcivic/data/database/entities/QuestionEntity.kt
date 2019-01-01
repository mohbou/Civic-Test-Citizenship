package com.mohbou.enhancedtestcivic.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "question_table",indices = [Index(value = ["id"])])
data class QuestionEntity(@PrimaryKey(autoGenerate = false)
                          @ColumnInfo(name = "id")
                          val id: String,
                          val question: String,
                          val review:Boolean=false)