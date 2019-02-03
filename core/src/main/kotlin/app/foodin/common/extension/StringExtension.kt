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
fun String.csvToList(): List<String> {
    return this.split(",").map { it.trim() }
}

fun String?.hasValue(): Boolean {
    return this != null && !this.isEmpty()
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
        if (it.length < length) it else it.substring(0, length)  }
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
            val sub = s.substring( length - END_LENGTH)
            return if (sub!!.length == length - END_LENGTH) {
                sub!! + endStr
            } else {
                sub
            }
        }
    } else {
        return this
    }

}