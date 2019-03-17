package kr.co.lendit.proxy

import app.foodin.aop.ControllerHandler
import app.foodin.aop.requestLog.RequestLog
import app.foodin.common.utils.MDCUtils
import app.foodin.common.utils.MDCUtils.HEADER_MAP_MDC
import app.foodin.common.utils.MDCUtils.KEY_REQUEST_UID
import app.foodin.common.utils.MDCUtils.REQUEST_IP
import app.foodin.common.utils.MDCUtils.REQUEST_METHOD_MDC
import app.foodin.common.utils.MDCUtils.REQUEST_URI_MDC
import app.foodin.core.annotation.Loggable
import org.slf4j.Logger
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

// TODO: local cache
internal object ControllerHandlerFactory {
    fun getControllerHandler(meta: SignatureProcessor): ControllerHandler {
        val defaultHanlder = DefaultControllerHandler(meta)
        return when {
            meta.hasAnnotation(Loggable::class.java) -> LoggableControllerHandler(meta, defaultHanlder)
            else -> defaultHanlder
        }
    }
}

internal class DefaultControllerHandler(private val meta: SignatureProcessor) : ControllerHandler {
    override fun preControllerHandler(logger: Logger, request: HttpServletRequest, args: Array<Any>): RequestLog {

        val path = MDCUtils[REQUEST_URI_MDC] ?: ""
        val method = MDCUtils[REQUEST_METHOD_MDC] ?: ""

        val clientIp = MDCUtils[REQUEST_IP] ?: ""
        logger.info("====== REQUEST START(uri : {} - {}, ip : {} , headers : {} )", method, path, clientIp, MDCUtils[HEADER_MAP_MDC])
        RequestLog.start(method = method, path = path, eventId = MDCUtils[KEY_REQUEST_UID] ?: "", clientIp = clientIp)
        return RequestLog
    }

    override fun completeControllerHandler(logger: Logger, request: HttpServletRequest, response: Any?): RequestLog {
        var status = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response?.status
        val path = MDCUtils[REQUEST_URI_MDC]
        val method = MDCUtils[REQUEST_METHOD_MDC]
        logger.info("====== REQUEST END(uri : {} - {}, executeTime : {} ms)", method, path, RequestLog.calExecuteTime())
        RequestLog.finish(status ?: 0)
        return RequestLog
    }
}

internal class LoggableControllerHandler : ControllerHandler {
    companion object {
        @JvmStatic
        private val LOGGABLE_WHITELIST_ANN = listOf(RequestBody::class.java, RequestParam::class.java, ModelAttribute::class.java)
    }

    private val meta: SignatureProcessor
    private val handler: ControllerHandler
    private val loggable: Loggable

    constructor(meta: SignatureProcessor, handler: ControllerHandler) {
        this.meta = meta
        this.handler = handler
        loggable = meta.getAnnotation(Loggable::class.java) as Loggable
    }

    override fun preControllerHandler(logger: Logger, request: HttpServletRequest, args: Array<Any>): RequestLog {

        val requestLog = handler.preControllerHandler(logger, request, args)
        var loggable: Boolean
        val parameterPairs = meta.parameterPairs

        if (this.loggable.param) {
            args.forEachIndexed { idx, arg ->
                val pair = parameterPairs.getOrNull(idx)
                loggable = LOGGABLE_WHITELIST_ANN.any { pair?.second?.getAnnotation(it) != null }
                if (loggable && pair != null) {
                    RequestLog.params()[pair.first] = arg
                }
            }
        }
        return requestLog
    }

    override fun completeControllerHandler(logger: Logger, request: HttpServletRequest, response: Any?): RequestLog {
        return when (this.loggable.result) {
            true -> {
                handler.completeControllerHandler(logger = logger, request = request).also { it.response = response }
            }
            false -> handler.completeControllerHandler(logger = logger, request = request)
        }
    }
}
