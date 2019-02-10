package app.foodin.config

import app.foodin.domain.user.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomJwtTokenEnhancer : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken?,
                         authentication: OAuth2Authentication): OAuth2AccessToken {
        val additionalInfo = HashMap<String, Any>()
        val user : User = authentication.principal as User
        additionalInfo["user_id"] = user.id ?: 0
        additionalInfo["user_real_name"] = user.realName
        if(user.nickName != null) {
            additionalInfo["user_nick_name"] = user.nickName!!
        }
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }

}