package com.motionweb.halal.utils


/**
 * return true if strings is equal
 * else return false
 * */
fun String.similarityCheck(text: String): Boolean {
    if (this.equals(text, ignoreCase = false)) return true
    return false
}

fun String.toImageUrl(): String = this.replace("web:8001", "159.65.120.217")
