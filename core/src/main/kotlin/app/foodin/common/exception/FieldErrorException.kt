package app.foodin.common.exception

import org.springframework.validation.Errors
import org.springframework.validation.FieldError

/**
 * 필드 오류
 * code : INVALID_FIELDS
 * message : {ex.invalid_fields}
 */
class FieldErrorException : CommonException {

    companion object {
        const val CODE = "INVALID_FIELDS"
        const val MSG_CODE = "{ex.invalid_fields}"
    }

    var fields: List<CustomFieldError> = ArrayList()

    constructor(errors: Errors) : super(CODE, null, MSG_CODE) {
        fields = errors.fieldErrors.map { CustomFieldError(it) }
    }

    constructor(
        field: String,
        msgCode: String,
        vararg msgArgs: String
    ) : super(CODE, null, MSG_CODE) {
        fields = arrayListOf(CustomFieldError(field, msgCode, msgArgs))
    }

    class CustomFieldError(
        objectName: String,
        field: String,
        val message: String,
        val args: Array<out String>? = null
    ) : FieldError(objectName, field, "") {

        constructor(fieldError: FieldError) : this(
            fieldError.objectName,
            fieldError.field,
            fieldError.defaultMessage ?: ""
        )

        constructor(field: String,
                    message: String,
                    args: Array<out String>
        ) : this("", field, message, args)
    }

}
