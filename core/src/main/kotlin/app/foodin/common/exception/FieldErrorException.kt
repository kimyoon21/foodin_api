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
        const val MSG_CODE = EX_INVALID_FIELD
    }

    var fields: List<CustomFieldError> = ArrayList()

    constructor(errors: Errors) : super(msgCode=MSG_CODE) {
        fields = errors.fieldErrors.map { CustomFieldError(it) }
    }

    constructor(
        field: String,
        msgCode: String,
        vararg msgArgs: String
    ) : super(msgCode=MSG_CODE) {
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

        constructor(
            field: String,
            message: String,
            args: Array<out String>
        ) : this("", field, message, args)
    }
}
