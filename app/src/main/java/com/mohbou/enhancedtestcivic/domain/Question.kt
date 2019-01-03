package com.mohbou.enhancedtestcivic.domain


import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import java.util.*

data class Question(@ColumnInfo(name = "id")
                    var id:String= UUID.randomUUID().toString(),
                    @ColumnInfo(name = "question")
                    var question: String?="",
                    @Ignore
                    @SerializedName(value = "answerResponse")
                    var answers:List<Answer>?= emptyList(),
                    @ColumnInfo(name = "review")
                    var review:Boolean?=false)