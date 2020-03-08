package app.foodin.common.exception

import app.foodin.common.result.ExceptionResult
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import java.util.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotExistsException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleNotExistsException(ex: NotExistsException, request: WebRequest): ExceptionResult {
        logger.info(" ****** NotExistsException : " + ex.localizedMessage)
        return ExceptionResult(request, ex)
    }

    @ExceptionHandler(CommonException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleCommonException(ex: CommonException, request: WebRequest): ExceptionResult {
        logger.info(" ****** CommonException : " + ex.localizedMessage)
        val cause = ex.cause
        // http 오류는 해당 바디에 오류내용이 있을 때가 많으므로 그걸 넣어준다
        val debugMessage = if (cause is HttpClientErrorException) {
            cause.responseBodyAsString
        } else {
            cause?.message
        }
        return ExceptionResult(request, ex, debugMessage)
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): ExceptionResult {
        logger.info(" ****** AccessDeniedException : " + ex.localizedMessage)
        return ExceptionResult(request, EX_AUTH_FAILED, "권한이 없습니다", ex.localizedMessage, null)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleExceptions(ex: Exception, request: WebRequest): ExceptionResult {
        logger.info(" ****** other Exceptions : " + ex.localizedMessage)
        return ExceptionResult(request, ex)
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info(" ****** BindException : " + ex.localizedMessage)
        val field = ex.bindingResult.fieldError?.field
        val failureResult = ExceptionResult(request, EX_INVALID_REQUEST, "$field 값이 잘못되거나 부족합니다", null, null)
        failureResult.debugMessage = ex.bindingResult.fieldError.toString()
        return ResponseEntity(failureResult, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.info(" ****** HttpMessageNotReadableException : " + ex.localizedMessage)
        val failureResult = ExceptionResult(request, EX_INVALID_REQUEST, "입력값이 잘못되거나 부족합니다", null, null)
        val cause = ex.cause
        if (cause is MissingKotlinParameterException) {
            failureResult.debugMessage = cause.path[0].description
        }
        return ResponseEntity(failureResult, HttpStatus.BAD_REQUEST)
    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.info(" ****** NoHandlerFoundException : " + ex.localizedMessage)
        val responseBody = HashMap<String, String>()
        responseBody["path"] = request.contextPath
        responseBody["message"] = "뭔 오류여 "
        return ResponseEntity(responseBody, HttpStatus.NOT_FOUND)
    }
}
