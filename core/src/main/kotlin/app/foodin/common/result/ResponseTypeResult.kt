package app.foodin.common.result

open class ResponseTypeResult<T>(
    open var message: String? = "ok",
    open var data: T? = null
) {

    open var succeeded: Boolean = true

    constructor(successData: T?) : this(data = successData)
}
