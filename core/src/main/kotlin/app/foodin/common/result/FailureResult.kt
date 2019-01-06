package app.foodin.common.result

import app.foodin.common.exception.CommonException
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.WebRequest

class FailureResult(
    var code: String,
    var message: String? = null,
    var data: Any? = null,
    var ref: Any? = null
) {

    private val logger = LoggerFactory.getLogger(FailureResult::class.java)

    constructor(request: WebRequest, code: String, message: String?, data: Any?) : this(
        code,
        message,
        data,
        request.getDescription(true)
    )

    constructor(request: WebRequest, exception: CommonException, message: String) : this(
        request,
        exception.code,
        message,
        exception.data
    )

    constructor(request: WebRequest, exception: Throwable) : this(
        request,
        "UNKNOWN",
        exception.message,
        null
    )
}
