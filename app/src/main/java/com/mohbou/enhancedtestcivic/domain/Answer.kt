package com.mohbou.enhancedtestcivic.domain

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.util.*

data class Answer(@ColumnInfo(name = "id")
                  var id: String =UUID.randomUUID().toString(),
                  @ColumnInfo(name = "answer")
                  @SerializedName(value = "answer")
                  val answer: String)