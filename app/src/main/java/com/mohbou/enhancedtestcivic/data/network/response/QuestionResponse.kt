package com.mohbou.enhancedtestcivic.data.network.response

import java.util.*

data class QuestionResponse(val id: UUID= UUID.randomUUID(),
                            val question:String?="",
                            var answers:List<AnswerResponse>?= emptyList(),
                            var review:Boolean?=false)