package app.foodin.domain.user

import app.foodin.common.enums.SnsType

data class SnsTokenDTO(
        val snsUserId : String,
        val snsType : SnsType,
        val accessToken : String
)

