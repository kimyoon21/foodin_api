package app.foodin.config

import app.foodin.common.utils.CustomJwtUserInfo
import app.foodin.domain.user.User
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomJwtTokenEnhancer : TokenEnhancer {
    override fun enhance(
        accessToken: OAuth2AccessToken?,
        authentication: OAuth2Authentication
    ): OAuth2AccessToken {
        val additionalInfo = HashMap<String, Any>()
        val user: User = authentication.principal as User
        additionalInfo["userInfo"] = CustomJwtUserInfo(user.id, user.username, user.nickName, user.realName, user.snsType)
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }
}