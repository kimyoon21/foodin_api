package app.foodin.common.result

import app.foodin.common.exception.CommonException
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.WebRequest

class FailureResult(
        var code: String,
        override var message: String? = null,
        var debugMessage: String? = null,
        override var data: Any? = emptyMap<String, String>()
) : ResponseResult(message, data) {

    override var succeeded: Boolean = false

    private val logger = LoggerFactory.getLogger(FailureResult::class.java)

    constructor(request: WebRequest, code: String, message: String?, debugMessage: String?, data: Any?) : this(
            code,
            message,
            debugMessage,
            data
    )

    constructor(request: WebRequest, code: String, debugMessage: String?, data: Any?) : this(
            code,
            null,
            debugMessage,
            data
    )

    constructor(request: WebRequest, exception: CommonException, debugMessage: String?) : this(
            request,
            exception.code,
            exception.localizedMessage,
            debugMessage,
            exception.data
    )

    constructor(request: WebRequest, exception: Throwable) : this(
            request,
            "UNKNOWN",
            null,
            exception.message,
            null
    )
}
