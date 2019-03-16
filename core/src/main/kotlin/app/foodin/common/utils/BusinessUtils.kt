package app.foodin.common.utils

import java.util.*

fun getCleanUUID(): String {
    return UUID.randomUUID().toString().replace("-", "")
}