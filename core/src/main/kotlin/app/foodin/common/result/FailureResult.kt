package app.foodin.common.result

import app.foodin.common.exception.CommonException
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest

class FailureResult(
        var code: String,
        override var message: String? = null,
        override var data: Any? = null,
        var ref: Any? = null
) : ResponseResult(message,data){

    override val succeeded: Boolean = false

    private val logger = LoggerFactory.getLogger(FailureResult::class.java)

    constructor(request: WebRequest, code: String, message: String?, data: Any?) : this(
            code,
            message,
            data,
            request.getDescription(true)
    )

    constructor(request: HttpServletRequest, code: String, message: String?, data: Any?) : this(
        code,
        message,
        data,
        request.requestURI
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

    constructor(request: HttpServletRequest, exception: Throwable) : this(
            request,
            "UNKNOWN",
            exception.message,
            null
    )
}
