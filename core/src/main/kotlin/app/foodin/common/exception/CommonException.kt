package app.foodin.common.exception

open class CommonException(
    val code: String,
    val data: Any? = null,
    val msgCode: String,
    val msgArgs: Array<out String>? = null
) : Exception() {

    constructor(msgCode: String) : this("INVALID_REQUEST", null, msgCode, null)
}

/***
 *
 * CommonException Message type.
 * 아래 해당하는 오류타입에 따라서 메시징 처리. 아규먼트가 필요한 메시지인 경우 익셉션 생성시 파라미터를 메시지 코드로 추가.
 * 반복되지 않는 희귀케이스의 오류메시지인 경우, custom_msg 로 만든 다음 메시지 전체를 messgae.properties 에 추가해서 사용한다.
 */

/** 잘못된 접근입니다.  */
val EX_INVALID_REQUEST = "{ex.invalid.request}"
/** 존재하지 않습니다.  */
val EX_NOT_EXISTS = "{ex.not.exists}"
/** {0}이(가) 존재하지 않습니다.  */
val EX_NOT_EXISTS_WHAT = "{ex.not.exists.what}"
/** {0}이(가) 아닙니다.  */
val EX_NOT_A = "{ex.not_a}"
/** 이미 존재합니다.  */
val EX_ALREADY_EXISTS = "{ex.already.exists}"
/** {0} 이(가) 이미 존재합니다.  */
val EX_ALREADY_EXISTS_WHAT = "{ex.already.exists.what}"
/** 이미 삭제되었습니다.  */
val EX_ALREADY_DELETED = "{ex.already.deleted}"
/** {0} 이(가) 이미 삭제되었습니다.  */
val EX_ALREADY_DELETED_WHAT = "{ex.already.deleted.what}"
/** 이미 요청하였습니다.  */
val EX_ALREADY_REQUESTED = "{ex.already.requested}"
/** 이미 만료되었습니다.  */
val EX_ALREADY_EXPIRED = "{ex.already.expired}"
/** {0} 이(가) 이미 사용되고있습니다.  */
val EX_ALREADY_USED_WHAT = "{ex.already.used.what}"
/** {0}에 실패했습니다.  */
val EX_FAILED = "{ex.failed.what}"
/** {0} 할 수 없습니다.  */
val EX_CANNOT = "{ex.cannot.what}"
/** 접근이 거부되었습니다.  */
val EX_ACCESS_DENIED = "{ex.access_denied}"
/** 인증번호 발송에 실패했습니다. 번호를 다시 한 번 확인해주세요.  */
val EX_FAILED_AUTHNUM = "{ex.failed.authnum}"
/** 인증번호 입력 시간을 초과했습니다. 인증번호를 다시 받아 주십시오.  */
val EX_EXPIRED_AUTHNUM = "{ex.expired.authnum}"
/** 로그인에 실패했습니다. 아이디 혹은 비밀번호를 확인해주세요.  */
val EX_FAILED_LOGIN = "{ex.failed.login}"
/** 해당 기능에 대한 권한이 없습니다.  */
val EX_NOT_PERMITTED = "{ex.not_permitted}"
/** 사용되지 않는 기능입니다.  */
val EX_DEPRECATED = "{ex.deprecated}"

/** 오류가 발생하였습니다!  */
val EX_DEFAULT = "{ex.default}"

/** {0} : 필수 항목입니다.  */
val EX_NEED = "{ex.need}"

/** 해당 기능에 대한 권한이 없습니다.  */
val EX_AUTH_FAILED = "{ex.auth_failed}"

