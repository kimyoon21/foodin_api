package app.foodin.core.service

import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.exception.NotExistsException
import app.foodin.common.utils.USERNAME_SEPERATOR
import app.foodin.common.utils.createBasicAuthHeaders
import app.foodin.core.gateway.SessionLogGateway
import app.foodin.core.gateway.UserGateway
import app.foodin.domain.sessionLog.SessionLog
import app.foodin.domain.user.*
import java.sql.Timestamp
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

interface UserService {
    fun findByLoginId(loginId: String): User?
    fun findByEmail(email: String): User?
    fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User?
    fun saveFrom(userCreateReq: UserCreateReq): User
    fun update(userId: Long, userUpdateReq: UserUpdateReq): User
    fun loggedIn(user: User, token: String, refreshToken: String, expiration: Date): UserLoginResultDto
    fun findAll(): List<User>
    fun emailLogin(emailLoginDto: EmailLoginDto): UserLoginResultDto
    fun snsLogin(snsTokenDto: SnsTokenDto, user: User): UserLoginResultDto
    fun checkValidUserInfo(snsTokenDto: SnsTokenDto): Boolean
    fun findById(id: Long): User
    fun disable(userId: Long): Boolean
}

@Service
@Transactional
class CustomUserDetailsService(
    private val userGateway: UserGateway,
    private val sessionLogGateway: SessionLogGateway,
    private val clientRegistrationRepository: ClientRegistrationRepository
) : UserService, UserDetailsService {

    private val logger = LoggerFactory.getLogger(CustomUserDetailsService::class.java)

    override fun findAll(): List<User> {
        return userGateway.findAll()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw CommonException(EX_NEED)
        val snsType: SnsType = SnsType.valueOf(username.split(USERNAME_SEPERATOR)[0].toUpperCase())
        val snsUserId = username.replaceFirst(snsType.name + USERNAME_SEPERATOR, "")

        return userGateway.findBySnsTypeAndSnsUserId(snsType, snsUserId)
                ?: throw UsernameNotFoundException("User not found")
    }

    override fun loggedIn(user: User, token: String, refreshToken: String, expiration: Date): UserLoginResultDto {

        val expireTime = Timestamp(expiration.time)
        sessionLogGateway.saveFrom(SessionLog(userId = user.id, token = token, expireTime = expireTime))

        return UserLoginResultDto(user, token, refreshToken, expireTime)
    }

    override fun findById(id: Long): User {
        return userGateway.findById(id) ?: throw NotExistsException(msgArgs = *arrayOf("유저"))
    }

    override fun disable(userId: Long): Boolean {
        val cnt = userGateway.updateEnabled(userId, false)
        return cnt > 0
    }

    override fun saveFrom(userCreateReq: UserCreateReq): User {
        return userGateway.saveFrom(userCreateReq.toUser())
    }

    override fun update(userId: Long, userUpdateReq: UserUpdateReq): User {
        return userGateway.updateFrom(userId, userUpdateReq)
    }

    override fun findByLoginId(loginId: String): User? {
        return userGateway.findByLoginId(loginId)
    }

    override fun findByEmail(email: String): User? {
        return userGateway.findByEmail(email)
    }

    override fun findBySnsTypeAndSnsUserId(snsType: SnsType, uid: String): User? {
        return userGateway.findBySnsTypeAndSnsUserId(snsType, uid)
    }

    override fun emailLogin(emailLoginDto: EmailLoginDto): UserLoginResultDto {
        val registration = clientRegistrationRepository.findByRegistrationId(SnsType.EMAIL.name.toLowerCase())
        val tokenUri = registration.providerDetails.tokenUri

        val restTemplate = RestTemplate()
        val headers = createBasicAuthHeaders("foo", "bar")
        headers.contentType = MediaType.APPLICATION_JSON
        val requestUriParam = "?grant_type=password&client_id=foo&scope=read&username=EMAIL::${emailLoginDto.email}&password=${emailLoginDto.loginPw}"

        val entity = HttpEntity(null, headers)
        try {
            val response = restTemplate.exchange(
                    tokenUri + requestUriParam,
                    HttpMethod.POST,
                    entity,
                    DefaultOAuth2AccessToken::class.java
            )

            response.body?.let {
                // user 정보 가져오기
                val user = findByEmail(emailLoginDto.email) ?: throw CommonException("잘못된 이메일")

                // 로그인 처리
                return loggedIn(user, it.value, it.refreshToken.value, it.expiration)
            } ?: throw CommonException("EMAIL 정보 오류")
        } catch (ex: HttpClientErrorException) {
            throw CommonException("email 혹은 비밀번호가 잘못되었습니다", ex)
        }
    }

    override fun snsLogin(snsTokenDto: SnsTokenDto, user: User): UserLoginResultDto {
        // sns 로그인 처리도 endpoint email 사용
        val registration = clientRegistrationRepository.findByRegistrationId(SnsType.EMAIL.name.toLowerCase())
        val tokenUri = registration.providerDetails.tokenUri

        val restTemplate = RestTemplate()
        val headers = createBasicAuthHeaders("foo", "bar")
        headers.contentType = MediaType.APPLICATION_JSON
        val requestUriParam = "?grant_type=password&client_id=foo&scope=read" +
                "&username=${snsTokenDto.snsType}::${snsTokenDto.snsUserId}&password=${snsTokenDto.snsUserId}"

        val entity = HttpEntity(null, headers)
        try {
            val response = restTemplate.exchange(
                    tokenUri + requestUriParam,
                    HttpMethod.POST,
                    entity,
                    DefaultOAuth2AccessToken::class.java
            )

            response.body?.let {

                // 로그인 처리
                return loggedIn(user, it.value, it.refreshToken.value, it.expiration)
            } ?: throw CommonException("SNS 정보 오류")
        } catch (ex: HttpClientErrorException) {
            throw CommonException("token 혹은 userId 가 잘못되었습니다", ex)
        }
    }

    private fun getSnsUserInfo(snsTokenDto: SnsTokenDto): MutableMap<out Any?, out Any?> {

        val registration = clientRegistrationRepository.findByRegistrationId(snsTokenDto.snsType.toString().toLowerCase())
        val userInfoEndpointUri = registration.providerDetails.userInfoEndpoint.uri
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${snsTokenDto.accessToken}")

        try {
            val entity = HttpEntity(null, headers)
            val response = restTemplate.exchange(
                    userInfoEndpointUri,
                    HttpMethod.GET,
                    entity,
                    MutableMap::class.java
            )

            response.body?.let {
                return it
            } ?: throw CommonException("SNS 정보 오류")
        } catch (ex: HttpClientErrorException) {
            logger.error(ex.responseBodyAsString, ex)
            throw CommonException("token 및 sns 정보가 정확하지 않습니다.", ex)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun checkValidUserInfo(snsTokenDto: SnsTokenDto): Boolean {

        // apple 우회
        if (SnsType.APPLE == snsTokenDto.snsType) {
            return SnsTokenDto(snsTokenDto.accessToken).snsUserId == snsTokenDto.snsUserId
        }

        val resultMap = getSnsUserInfo(snsTokenDto) as Map<String, Any>
        return when (snsTokenDto.snsType) {
            SnsType.KAKAO -> {
                val kakaoUserId = resultMap["id"] ?: throw CommonException("INVALID_KAKAO_RESULT")
                snsTokenDto.snsUserId == kakaoUserId.toString()
            }
            SnsType.NAVER -> {
                val naverUserId = (resultMap["response"] as Map<String, String>)["id"] ?: throw CommonException("INVALID_NAVER_RESULT")
                snsTokenDto.snsUserId == naverUserId
            }
            SnsType.FACEBOOK -> {
                val facebookUserId = resultMap["id"] ?: throw CommonException("INVALID_FACEBOOK_RESULT")
                snsTokenDto.snsUserId == facebookUserId
            }
            SnsType.EMAIL -> {
                throw CommonException("EMAIL 은 /email/login 사용")
            }
            else -> false
        }
    }
}
