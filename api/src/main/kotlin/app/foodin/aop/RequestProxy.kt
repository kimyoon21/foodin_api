package app.foodin.aop

import app.foodin.aop.requestLog.RequestLog
import kr.co.lendit.proxy.ControllerHandlerFactory
import kr.co.lendit.proxy.SignatureProcessor
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

internal interface ControllerHandler {
    fun preControllerHandler(logger: Logger, request: HttpServletRequest, args: Array<Any>): RequestLog
    fun completeControllerHandler(logger: Logger, request: HttpServletRequest, response: Any? = null): RequestLog
}

@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestControllerProxy {

    // 일반 로그에도 있고 별도의 로그 파일에도 있으면 그 로그 파일로만 request 분석을 할수 있어서 좋을듯
    // 새 Logger 가 필요하다
    private val log = LoggerFactory.getLogger(RequestControllerProxy::class.java)

    @Suppress("unused")
    @Pointcut("@within(app.foodin.core.annotation.IgnoreLoggable) || @annotation(app.foodin.core.annotation.IgnoreLoggable)")
    private fun hasIgnoreLoggable() {
    }

    @Suppress("unused")
    @Pointcut("execution(@(@org.springframework.web.bind.annotation.RequestMapping *) * *(..))")
    private fun hasRequestMapping() {
    }

    @Around("!hasIgnoreLoggable() && hasRequestMapping()")
    @Throws(Throwable::class)
    fun requestCheck(joinPoint: ProceedingJoinPoint): Any? {

        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        val signature = joinPoint.signature as MethodSignature
        val args = joinPoint.args

        val meta = SignatureProcessor(signature)

        val controllerHandler = ControllerHandlerFactory.getControllerHandler(meta)

        var result: Any? = null
        try {
            controllerHandler.preControllerHandler(log, request, args)
            result = joinPoint.proceed()
        } catch (e: Throwable) {
            log.info("Error at [${signature.declaringTypeName} : ${signature.name}]", e)
            result = e
            // 에러로그 2중으로 뿌려줌 에러는 exception handler 에서 처리
//            throw e
        } finally {
            // Status Logging
            controllerHandler.completeControllerHandler(log, request, result)
            log.info(RequestLog.json())
            RequestLog.close()
        }
        return result
    }
}
