package app.foodin.common.exception

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method


class CustomAsyncExceptionHandler : AsyncUncaughtExceptionHandler {

    private val logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler::class.java)

    override fun handleUncaughtException(
            throwable: Throwable, method: Method, vararg obj: Any) {

        logger.error("Async Exception message - " + throwable.message)
        logger.error("Async Exception cause - " + throwable.cause)
        logger.error(" =======> Method name - " + method.name)
        for (param in obj) {
            logger.error(" =======> Parameter value - $param")
        }
        throwable.printStackTrace()
    }

}