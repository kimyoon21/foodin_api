package app.foodin.common.exception

open class CommonException(
    open val data: Any? = null,
    open val msgCode: String,
    vararg msgArgs: String?
) : RuntimeException(msgCode) {
    constructor(msgCode: String) : this(null, msgCode, null)

    constructor(msgCode: String, ex: Throwable) : this(msgCode) {
        super.initCause(ex)
    }

    override val message: String?
        get() = super.message

//    constructor(msgCode: String, msgParam : String) : this(msgCode, null, msgCode, MsgUtil.msg(msgParam))
}

/***
 *
 * CommonException Message type.
 * 아래 해당하는 오류타입에 따라서 메시징 처리. 아규먼트가 필요한 메시지인 경우 익셉션 생성시 파라미터를 메시지 코드로 추가.
 * 반복되지 않는 희귀케이스의 오류메시지인 경우, custom_msg 로 만든 다음 메시지 전체를 messgae.properties 에 추가해서 사용한다.
 */

/** 잘못된 접근입니다.  */
const val EX_INVALID_REQUEST = "{ex.invalid_request}"
/** 잘못된 필드입니다.  */
const val EX_INVALID_FIELD = "{ex.invalid_field}"
/** 존재하지 않습니다.  */
const val EX_NOT_EXISTS = "{ex.not_exists}"
/** {0}이(가) 존재하지 않습니다.  */
const val EX_NOT_EXISTS_WHAT = "{ex.not_exists_what}"
/** {0}이(가) 아닙니다.  */
const val EX_NOT_A = "{ex.not_a}"
/** 이미 존재합니다.  */
const val EX_ALREADY_REGISTERED = "{ex.already_exists}"
/** 이미 존재합니다.  */
const val EX_ALREADY_EXISTS = "{ex.already_exists}"
/** {0} 이(가) 이미 존재합니다.  */
const val EX_ALREADY_EXISTS_WHAT = "{ex.already_exists_what}"
/** 이미 삭제되었습니다.  */
const val EX_ALREADY_DELETED = "{ex.already_deleted}"
/** {0} 이(가) 이미 삭제되었습니다.  */
const val EX_ALREADY_DELETED_WHAT = "{ex.already._deleted_what}"
/** 이미 요청하였습니다.  */
const val EX_ALREADY_REQUESTED = "{ex.already_requested}"
/** 이미 만료되었습니다.  */
const val EX_ALREADY_EXPIRED = "{ex.already_expired}"
/** {0} 이(가) 이미 사용되고있습니다.  */
const val EX_ALREADY_USED_WHAT = "{ex.already_used_what}"
/** {0}에 실패했습니다.  */
const val EX_FAILED = "{ex.failed_what}"
/** {0} 할 수 없습니다.  */
const val EX_CANNOT = "{ex.cannot_what}"
/** 접근이 거부되었습니다.  */
const val EX_ACCESS_DENIED = "{ex.access_denied}"
/** 로그인실패 아이디 혹은 비밀번호가 잘못되었다  */
const val EX_FAILED_LOGIN_IDPW = "{ex.failed_login_idpw}"
/** 로그인실패 sns 계정연결이 안되었다  */
const val EX_FAILED_LOGIN_SNS = "{ex.failed_login_sns}"
/** 해당 기능에 대한 권한이 없습니다.  */
const val EX_NOT_PERMITTED = "{ex.not_permitted}"
/** 사용되지 않는 기능입니다.  */
const val EX_DEPRECATED = "{ex.deprecated}"

/** 오류가 발생하였습니다!  */
const val EX_DEFAULT = "{ex.default}"

/** {0} : 필수 항목입니다.  */
const val EX_NEED = "{ex.need}"

/** 계정 인증 관련 오류입니다.  */
const val EX_AUTH_FAILED = "{ex.auth_failed}"
