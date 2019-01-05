package app.foodin.domain

data class SnsTokenDto(
    val snsUserId : String,
    val snsType : SnsType,
    val accessToken : String
)

