package app.foodin.config

object SecurityConstants {
    val SECRET = "SecretKeyJwt"
    val EXPIRATION_TIME: Long = 864000000 // 10 days
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val SIGN_UP_URL = "/users/sign-up"
}
