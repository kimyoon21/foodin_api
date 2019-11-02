package app.foodin.domain.user

import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.extension.orElseThrow
import app.foodin.common.utils.JwtUtils
import com.google.gson.Gson

data class SnsTokenDto(
    val snsUserId: String,
    val snsType: SnsType,
    val accessToken: String
) {
    constructor(appleJwt: String) : this(getAppleIdFromJwt(appleJwt), SnsType.APPLE, appleJwt)
}

fun getAppleIdFromJwt(jwt: String): String {

    // jwt 핸들링
    val result = JwtUtils.decoded(jwt)
    val jsonResult = Gson().fromJson(result, Map::class.java) as Map<String, String>
    return jsonResult["sub"].orElseThrow { CommonException("입력받은 apple jwt 오류 ") }
}
