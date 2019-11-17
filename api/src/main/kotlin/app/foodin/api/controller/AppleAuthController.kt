package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.result.ResponseResult
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/apple-auth")
class AppleAuthController() {

    private val logger = LoggerFactory.getLogger(AppleAuthController::class.java)

    @PostMapping
    fun callbackFromWeb(@ModelAttribute appleResult: AppleAuthResult): ResponseResult {
        return handling(appleResult,"")
    }

    @PostMapping("/app")
    fun callbackFromApp(@RequestBody appleResult: AppleAuthResult): ResponseResult {
        return handling(appleResult, "/app")
    }


    fun handling(appleResult: AppleAuthResult, path: String): ResponseResult {
        val clientId = "app.foodin.client"
        val clientSecret = "eyJhbGciOiJFUzI1NiIsImtpZCI6Iko3NVU2MlkyWjcifQ.eyJpc3MiOiJHNDdWRDRSNzc3IiwiYXVkIjoiaHR0cHM6Ly9hcHBsZWlkLmFwcGxlLmNvbSIsInN1YiI6ImFwcC5mb29kaW4uY2xpZW50IiwiaWF0IjoxNTcyMTE3MTY4LCJleHAiOjE1ODA3NTcxNjh9.h-cZvEpYU3MZ0uF7IHKzOwKuxZ0h271OiQIlXfBWHNCqWdL-gMp3pj1L0l-mwjGjTCU1SGKwqlqGL3KesgHKTQ"
        val redirectUri = "https://foodin.app/apple-auth$path"
        val restTemplate = RestTemplate()

        val endpoint = "https://appleid.apple.com/auth/token"
        val parameters = LinkedMultiValueMap<String, String>()
        parameters["grant_type"] = "authorization_code"
        parameters["code"] = appleResult.code
        parameters["scope"] = "name email"
        parameters["redirect_uri"] = redirectUri
        parameters["client_id"] = clientId
        parameters["client_secret"] = clientSecret
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        try {
            val entity = HttpEntity(parameters, headers)
            val response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    entity,
                    MutableMap::class.java
            )

            response.body?.let {
                return ResponseResult(it)
            } ?: throw CommonException("APPLE 정보 오류")
        } catch (ex: HttpClientErrorException) {
            logger.error(ex.responseBodyAsString, ex)
            throw CommonException("token 및 sns 정보가 정확하지 않습니다.", ex)
        }
    }
}

data class AppleAuthResult(
        val code: String,
        val id_token: String
)
