package app.foodin.common.result

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class FailureResponseEntity(
        val failureResult: FailureResult
) : ResponseEntity<FailureResult>(failureResult,HttpStatus.OK) {

}
