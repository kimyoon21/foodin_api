package app.foodin.common.exception

import app.foodin.common.result.FailureResult
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
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
        logger.info(" ****** CommonException : " + ex.localizedMessage)
        return FailureResult(request, ex, ex.cause?.message)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): FailureResult {
        logger.info(" ****** AccessDeniedException : " + ex.localizedMessage)
        return FailureResult(request, "AUTH_FAILED", ex.localizedMessage, null)
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception, request: WebRequest): FailureResult {
        logger.info(" ****** other Exceptions : " + ex.localizedMessage)
        return FailureResult(request, ex)
    }

    override fun handleBindException(
            ex: BindException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.info(" ****** BindException : " + ex.localizedMessage)
        val field = ex.bindingResult.fieldError?.field
        val failureResult = FailureResult(EX_INVALID_REQUEST, "$field 값이 잘못되거나 부족합니다", null, null, null)
        failureResult.debugMessage = ex.bindingResult.fieldError.toString()
        return ResponseEntity(failureResult, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.info(" ****** HttpMessageNotReadableException : " + ex.localizedMessage)
        val failureResult = FailureResult(EX_INVALID_REQUEST, "입력값이 잘못되거나 부족합니다", null, null, null)
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