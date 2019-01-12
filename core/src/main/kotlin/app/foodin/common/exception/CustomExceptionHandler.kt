package app.foodin.common.exception

import app.foodin.common.result.FailureResult
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletRequest




@ControllerAdvice
@RestController
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(ex: CommonException, request: WebRequest): FailureResult {
        logger.error(ex.localizedMessage, ex)

        return FailureResult(request, ex, ex.localizedMessage!!)
    }

    @ExceptionHandler(Throwable::class)
    fun handleControllerException(ex: Throwable, request: HttpServletRequest): FailureResult {
        logger.error(ex.localizedMessage, ex)

        return FailureResult(request, ex)
    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val responseBody = HashMap<String, String>()
        responseBody["path"] = request.contextPath
        responseBody["message"] = "The URL you have reached is not in service at this time (404)."
        return ResponseEntity(responseBody, HttpStatus.NOT_FOUND)
    }
}