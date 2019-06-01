package app.foodin.common.result

import app.foodin.common.exception.CommonException
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.WebRequest

class ExceptionResult(
    var code: String,
    override var message: String? = null,
    var debugMessage: String? = null,
    override var data: Any? = emptyMap<String, String>()
) : ResponseResult(message, data) {

    override var succeeded: Boolean = false
    var ref: String? = null

    private val logger = LoggerFactory.getLogger(ExceptionResult::class.java)

    constructor(request: WebRequest, code: String, message: String?, debugMessage: String?, data: Any?) : this(
            code,
            message,
            debugMessage,
            data
    ) {
        ref = request.getDescription(false)
    }

    constructor(request: WebRequest, exception: CommonException, debugMessage: String? = null) : this(
            request,
            makeMsgCodeToCode(exception.msgCode),
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
/***
 * 정규식 사용법
 * val phoneNumber :String? = Regex(pattern = """\d{3}-\d{3}-\d{4}""")
.find(input = "phone: 123-456-7890, e..")?.value // phoneNumber: 123-456-7890
 *
 */
fun makeMsgCodeToCode(msgCode: String): String {
    return Regex(pattern = "\\{ex\\.(.*)}").find(msgCode)?.groupValues?.get(1)?.toUpperCase() ?: "INVALID_REQUEST"
}
