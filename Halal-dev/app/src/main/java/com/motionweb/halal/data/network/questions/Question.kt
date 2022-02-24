package com.motionweb.halal.data.network.questions

data class Question(
    val id: Int? = null,
    val author: String,
    val question: String,
    val answer: String? = null
)