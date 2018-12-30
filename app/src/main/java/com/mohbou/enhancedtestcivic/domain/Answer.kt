package com.mohbou.enhancedtestcivic.domain

import java.util.*

data class Answer(val id: String =UUID.randomUUID().toString(), val answer: String)