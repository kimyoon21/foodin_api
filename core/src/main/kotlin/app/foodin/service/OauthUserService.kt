package app.foodin.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.FieldErrorException
import app.foodin.common.result.ResponseResult
import app.foodin.domain.SnsTokenDTO
import app.foodin.domain.User
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.client.RestTemplate
import javax.validation.Valid

interface OauthUserService {
    fun checkUserInfoByAccessToken(snsTokenDTO: SnsTokenDTO): User
}

@Service
class OauthUserService(): OauthUserService {

}