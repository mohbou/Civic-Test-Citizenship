package com.mohbou.enhancedtestcivic.domain

import java.util.*

data class Answer(var id: String =UUID.randomUUID().toString(),
                  var answer: String)