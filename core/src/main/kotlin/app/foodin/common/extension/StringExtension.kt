package app.foodin.common.extension

/**
 * usage:
 * ```
 * authenticationRequest.email
 *  .orElseThrow { CommonException("") }
 *  .let { checkRegisteredEmail(it) }
 * ```
 */
inline fun String.csvToList(): List<String> {
    return this.split(",").map { it.trim() }
}


