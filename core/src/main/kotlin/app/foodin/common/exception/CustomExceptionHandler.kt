package app.foodin.common.exception

import app.foodin.common.result.FailureResult
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CommonException::class)
    fun handleCommonException (ex : CommonException , request : WebRequest) : FailureResult {
        return FailureResult(request,ex,ex.localizedMessage!!)
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException (ex : Exception , request : WebRequest) : FailureResult {
       return FailureResult(request,ex)
    }
}