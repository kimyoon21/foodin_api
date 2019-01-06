package app.foodin.domain

data class SnsTokenDTO(
    val snsUserId : String,
    val snsType : SnsType,
    val accessToken : String
)

