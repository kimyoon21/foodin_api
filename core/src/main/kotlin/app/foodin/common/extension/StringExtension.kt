package app.foodin.common.extension

import app.foodin.common.exception.CommonException

/**
 * usage:
 * ```
 * authenticationRequest.email
 *  .orElseThrow { CommonException("") }
 *  .let { checkRegisteredEmail(it) }
 * ```
 */
fun String?.csvToList(): MutableList<String> {
    return if (this.hasValue()) {
        this!!.split(",").map { it.trim() }.filter { it.hasValue() }.toMutableList()
    } else {
        mutableListOf()
    }
}

fun MutableList<String>.listToCsv(): String {
    return this.filter { it.trim().hasValue() }.joinToString(",")
}

fun String?.tagsToList(): MutableList<String> {
    return if (this.hasValue()) {
        this!!.split(" ").map { it.trim() }.filter { it.hasValue() }.toMutableList()
    } else {
        mutableListOf()
    }
}

fun MutableList<String>.listToTags(): String {
    return this.filter { it.trim().hasValue() }.joinToString(" ")
}

fun String?.hasValue(): Boolean {
    return this != null && !this.isEmpty()
}

fun <T> Collection<T>?.hasValue(): Boolean {
    return this != null && !this.isEmpty() && this.stream().anyMatch { x -> x != null }
}

/**
 * String.substring 의 예외처리 추가한 버전
 *
 * @param s
 * @param length
 * @return
 */
fun String?.substring(length: Int): String? {

    return this?.let {
        if (it.length < length) it else it.substring(0, length) }
}

/**
 * String.substring 의 예외처리 및 길이초과시 마지막 스트링붙임 버전
 *
 * @param s
 * @param length
 * @return
 */
fun String?.substring(length: Int, endStr: String): String? {
    val END_LENGTH = endStr.length
    if (length <= END_LENGTH) {
        throw CommonException("lengh must be greater than $END_LENGTH")
    }

    if (this.hasValue()) {
        val s = this!!
        if (this.length <= length) {
            return s
        } else {
            val sub = s.substring(length - END_LENGTH)
            return if (sub!!.length == length - END_LENGTH) {
                sub + endStr
            } else {
                sub
            }
        }
    } else {
        return this
    }
}

fun String.toPascalCase(): String {
    return this.split('_').map {
        it.capitalize() } .joinToString("")
}

fun String.toSnakeCase(): String {
    var text: String = ""
    var isFirst = true
    this.forEach {
        if (it.isUpperCase()) {
            if (isFirst) isFirst = false
            else text += "_"
            text += it.toLowerCase()
        } else {
            text += it
        }
    }
    return text
}