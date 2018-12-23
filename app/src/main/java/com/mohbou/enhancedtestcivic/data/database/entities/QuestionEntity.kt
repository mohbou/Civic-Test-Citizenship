package com.mohbou.enhancedtestcivic.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.mohbou.enhancedtestcivic.domain.Answer
import java.util.*

@Entity(tableName = "question",indices = [Index(value = ["id"],unique = true)])
data class QuestionEntity(@PrimaryKey
                          val id: String = UUID.randomUUID().toString(),
                          val question: String,
                          val review:Boolean)