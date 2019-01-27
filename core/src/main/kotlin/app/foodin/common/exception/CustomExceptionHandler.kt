package app.foodin.common.exception

import app.foodin.common.result.FailureResult
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(ex: CommonException, request: WebRequest): FailureResult {
//        logger.error(ex.localizedMessage, ex)
        logger.info(" ****** CommonException : " + ex.localizedMessage)
        return FailureResult(request, ex, ex.localizedMessage!!)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): FailureResult {
        logger.info(" ****** AccessDeniedException : " +ex.localizedMessage)
        return FailureResult(request,"AUTH_FAILED", ex.localizedMessage,null)
    }


    @ExceptionHandler(Exception::class)
    fun handleError(ex: Exception, request: WebRequest): FailureResult {
//        logger.error(ex.localizedMessage, ex)
        logger.info(" ****** other Exceptions : " +ex.localizedMessage)
        return FailureResult(request, ex)
    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val responseBody = HashMap<String, String>()
        responseBody["path"] = request.contextPath
        responseBody["message"] = "뭔 오류여 "
        return ResponseEntity(responseBody, HttpStatus.NOT_FOUND)
    }
}