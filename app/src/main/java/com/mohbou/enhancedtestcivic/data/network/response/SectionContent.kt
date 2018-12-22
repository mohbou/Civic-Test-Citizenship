package com.mohbou.enhancedtestcivic.data.network.response



data class SectionContent(
    val questionResponse: List<QuestionResponse>,
    val sub_section_name: String
)