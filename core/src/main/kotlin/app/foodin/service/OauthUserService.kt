package app.foodin.service

import app.foodin.domain.SnsTokenDto
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
    fun checkUserInfoByAccessToken(snsTokenDto: SnsTokenDto): User
}

@Service
class OauthUserService(): OauthUserService {
    override fun checkUserInfoByAccessToken(
    snsTokenDto: SnsTokenDto
    ): ResponseResult {
        // TODO 필드가 null 이거나 맞지 않는 타입일 때 아무런 메시지 없이 400 에러 발생함.

        if(errors.hasErrors()) {
            throw FieldErrorException(errors)
        }

        val registration = clientRegistrationRepository.findByRegistrationId(snsTokenDTO.snsType.toString().toLowerCase())
        val userInfoEndpointUri = registration.providerDetails.userInfoEndpoint.uri

        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${snsTokenDTO.token}")

        val entity = HttpEntity(null, headers)
        val response = restTemplate.exchange(
                userInfoEndpointUri,
                HttpMethod.GET,
                entity,
                Map::class.java
        )
        response.body?.let {
            return ResponseResult(it)
        }

        throw CommonException("데이터가 없습니다.")
    }


}