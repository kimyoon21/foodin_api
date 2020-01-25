package app.foodin.config

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_AUTH_FAILED
import app.foodin.common.utils.CustomJwtUserInfo
import app.foodin.common.utils.JsonUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

class CustomJwtAccessTokenConverter : JwtAccessTokenConverter() {

    override fun extractAuthentication(map: Map<String, *>): OAuth2Authentication {
        val authentication = super.extractAuthentication(map)
        var userAuthentication: Authentication? = authentication.userAuthentication

        if (userAuthentication != null) {
            val userInfoMap = map["userInfo"] ?: throw CommonException(EX_AUTH_FAILED)
            val userInfo = JsonUtils.mapTo<CustomJwtUserInfo>(userInfoMap)

            val authorities = userAuthentication.authorities

            userAuthentication = UsernamePasswordAuthenticationToken(userInfo,
                    userAuthentication.credentials, authorities)
        }
        return OAuth2Authentication(authentication.oAuth2Request, userAuthentication)
    }
}
