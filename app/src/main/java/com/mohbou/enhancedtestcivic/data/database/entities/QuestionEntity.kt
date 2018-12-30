package com.mohbou.enhancedtestcivic.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.mohbou.enhancedtestcivic.domain.Answer
import java.util.*

@Entity(tableName = "question_table",indices = [Index(value = ["id"])])
data class QuestionEntity(@PrimaryKey(autoGenerate = false)
                          @ColumnInfo(name = "id")
                          val id: String,
                          val question: String,
                          val review:Boolean=false)