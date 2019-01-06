package app.foodin.controller

import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.FieldErrorException
import app.foodin.common.extension.hasValueOrElseThrow
import app.foodin.common.result.ResponseResult
import app.foodin.domain.user.SnsTokenDTO
import app.foodin.domain.user.User
import app.foodin.domain.user.UserRegisterDTO
import app.foodin.domain.user.UserService
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
        private val userService: UserService,
        private val clientRegistrationRepository: ClientRegistrationRepository
) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    private val passwordRegex = "^(?=.*[a-z])(?=.*[0-9]).{10,}$".toRegex(RegexOption.IGNORE_CASE)

    /**
     * 이미 가입되어 있다는 메시지와 함께, 어떤 SNS 로 가입했는지 예외 발생
     */
    fun throwAlreadyRegistered(registeredList: List<String>) {
        throw CommonException(
                "ALREADY_REGISTERED",
                registeredList,
                "이미 가입되어 있습니다."
        )
    }

    fun checkRegisteredEmail(email: String) {
        userService.findByEmail(email)?.let { user ->
            throwAlreadyRegistered(listOf()) //TODO
        }
    }

    fun checkRegisteredUid(snsType: SnsType, uid: String) {
        userService.findBySnsTypeAndSnsUserId(snsType, uid)?.let {
            throwAlreadyRegistered(listOf(it.snsType.toString()))
        }
    }

    fun checkPassword(password: String) {
        if(!passwordRegex.containsMatchIn(password)) {
            throw FieldErrorException("password", "{ex.invalid.password}")
        }
    }

    @PostMapping("/register")
    fun register(
            @ApiParam(value = "Authentication Request") @RequestBody @Valid userRegisterDTO: UserRegisterDTO,
            @ApiIgnore errors: Errors
    ): ResponseResult {
        logger.info("authRequest: $userRegisterDTO")

        if(errors.hasErrors()) {
            throw FieldErrorException(errors)
        }


        userRegisterDTO.email
                .hasValueOrElseThrow { FieldErrorException(userRegisterDTO::email.name, "{ex.need}", "{word.email}") }
                .let { checkRegisteredEmail(it) }


        if(userRegisterDTO.snsType != SnsType.EMAIL) {
            userRegisterDTO.snsUserId
                    .hasValueOrElseThrow { FieldErrorException(userRegisterDTO::snsUserId.name, "{ex.need}", "{word.uid}") }
                    .let { checkRegisteredUid(userRegisterDTO.snsType, it) }
        } else {
            userRegisterDTO.password
                    .hasValueOrElseThrow { FieldErrorException(userRegisterDTO::password.name, "{ex.need}", "{word.password}") }
                    .let { checkPassword(it) }
        }

        if(!userRegisterDTO.agreePolicy) {
            throw FieldErrorException(userRegisterDTO::agreePolicy.name, "{ex.need_to.agree_policy}")
        }

        userRegisterDTO.name
                .hasValueOrElseThrow { FieldErrorException(userRegisterDTO::name.name, "{ex.need}", "{word.name}") }

        return ResponseResult(userService.saveFrom(userRegisterDTO))
    }

    @GetMapping(value = "/email")
    fun userByEmail(@RequestParam email: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findByEmail(email))
    }

    /***
     * 1. token 에서 access_token , sns_type 등 체크
     * 2. 해당 auth server 에 info 체크
     * 3. 일치 시 이미 해당 정보 멤버 있는지 확인
     * 4. 없으면 없다는 response 로 등록 과정 유도
     * 5. 있으면 로그인 시키고 jwtToken 내려줌
     */
    @PostMapping(value = "/start")
    fun checkUserInfoByAccessToken(
            @RequestBody @Valid snsTokenDTO: SnsTokenDTO,
            errors: Errors
    ): ResponseResult {
        // TODO 필드가 null 이거나 맞지 않는 타입일 때 아무런 메시지 없이 400 에러 발생함.
        if (errors.hasErrors()) {
            throw FieldErrorException(errors)
        }
        // 해당 auth server 에 info 체크
        if(!checkValidUserInfo(snsTokenDTO)){
            throw CommonException("인증정보가 일치하지 않습니다.")
        }

        // 가입되어 있는지 체크
        val user = userService.findBySnsTypeAndSnsUserId(snsTokenDTO.snsType,snsTokenDTO.snsUserId)
        user?.let{
            return ResponseResult("go_to_register")
        }

        // 로그인 처리
        userService.loggedIn(user!!)


        throw CommonException("데이터가 없습니다.")
    }

    private fun checkValidUserInfo(snsTokenDTO: SnsTokenDTO): Boolean {
        val resultMap : Map<String,String> = getSnsUserInfo(snsTokenDTO).data as Map<String,String>
        when(snsTokenDTO.snsType){
            SnsType.KAKAO -> {
                val kakaoUserId = resultMap.get("id") ?: throw CommonException("INVALID_KAKAO_RESULT")
                return snsTokenDTO.snsUserId == kakaoUserId
            }
//            SnsType.NAVER ->{}
//            SnsType.FACEBOOK ->{}
//            SnsType.EMAIL ->{}
            else -> return false
        }
    }

    private fun getSnsUserInfo(snsTokenDTO: SnsTokenDTO) : ResponseResult {


        val registration = clientRegistrationRepository.findByRegistrationId(snsTokenDTO.snsType.toString().toLowerCase())
        val userInfoEndpointUri = registration.providerDetails.userInfoEndpoint.uri

        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer ${snsTokenDTO.accessToken}")

        val entity = HttpEntity(null, headers)
        val response = restTemplate.exchange(
                userInfoEndpointUri,
                HttpMethod.GET,
                entity,
                Map::class.java
        )
        response.body?.let {
            return ResponseResult(it)
        } ?: throw CommonException("SNS 정보 오류")
    }
}