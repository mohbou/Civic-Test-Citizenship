package com.mohbou.enhancedtestcivic.data.network.response

import java.util.*

data class QuestionResponse(val id: UUID= UUID.randomUUID(),
                            val question:String?="",
                            var answerResponse:List<AnswerResponse>?,
                            var review:Boolean?=false)