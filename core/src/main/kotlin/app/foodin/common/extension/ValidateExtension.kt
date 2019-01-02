package app.foodin.common.extension

/**
 * usage:
 * ```
 * authenticationRequest.email
 *  .orElseThrow { CommonException("") }
 *  .let { checkRegisteredEmail(it) }
 * ```
 */
inline fun <T, X : Throwable> T?.orElseThrow(supplier: () -> X): T {
    return this ?: throw supplier()
}

/**
 * hasValue : Not null and not empty
 */
inline fun <X : Throwable> String?.hasValueOrElseThrow(supplier: () -> X): String {
    return if(this != null && !this.isEmpty())
        this
    else
        throw supplier()
}

inline fun <T, R> T?.ifNullOrElse(ifNull: () -> R, orElse: (T) -> R): R {
    return if(this == null)
        ifNull()
    else
        orElse(this)
}

inline fun <R> String?.ifNullOrEmptyOtherwise(ifNull: () -> R, orElse: (String) -> R): R {
    // if문에 this.isNullOrEmpty() 만 넣고 싶었으나, 그럼 else 구문에서 오류가 발생하여 null 체크를 명시적으로 작성
    return if(this == null || this.isEmpty())
        ifNull()
    else
        orElse(this)
}
