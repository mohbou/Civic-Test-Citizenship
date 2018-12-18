package com.mohbou.enhancedtestcivic.domain


import java.util.*

data class Question(val id:UUID= UUID.randomUUID(),
                    val question: String?="",
                    var answers:List<Answer>?= emptyList(),
                    var review:Boolean?=false)