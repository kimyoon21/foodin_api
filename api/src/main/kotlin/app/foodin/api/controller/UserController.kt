package app.foodin.api.controller

import app.foodin.auth.CustomJwtUserInfo
import app.foodin.common.enums.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ALREADY_REGISTERED
import app.foodin.common.exception.FieldErrorException
import app.foodin.common.extension.throwNullOrEmpty
import app.foodin.common.result.ResponseResult
import app.foodin.core.annotation.Loggable
import app.foodin.core.service.UserService
import app.foodin.domain.user.EmailLoginDTO
import app.foodin.domain.user.SnsTokenDTO
import app.foodin.domain.user.User
import app.foodin.domain.user.UserRegDTO
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@Loggable(result = true, param = true)
class UserController(
    private val userService: UserService

) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    private val passwordRegex = "^(?=.*[a-z])(?=.*[0-9]).{8,}$".toRegex(RegexOption.IGNORE_CASE)

    @PutMapping("/{id}")
    fun update(
        @ApiParam(value = "Authentication Request") @RequestBody @Valid userUpdateDTO: UserRegDTO,
        @PathVariable("id") userId: Long,
        @ApiIgnore errors: Errors
    ): ResponseResult {
        logger.info("authRequest: $userUpdateDTO")

        return ResponseResult()
    }

    @PostMapping("/register")
    fun register(
        @ApiParam(value = "Authentication Request") @RequestBody @Valid userRegDTO: UserRegDTO,
        @ApiIgnore errors: Errors
    ): ResponseResult {
        logger.info("authRequest: $userRegDTO")

        if (errors.hasErrors()) {
            throw FieldErrorException(errors)
        }

        userRegDTO.email
                .throwNullOrEmpty { FieldErrorException(userRegDTO::email.name, "{ex.need}", "{word.email}") }
                .let {
                    checkRegisteredEmail(it)
                }

        if (userRegDTO.snsType != SnsType.EMAIL) {
            userRegDTO.snsUserId
                    .throwNullOrEmpty { FieldErrorException(userRegDTO::snsUserId.name, "{ex.need}", "{word.uid}") }
                    .let {

                        checkRegisteredUid(userRegDTO.snsType, it)
                        // sns 인경우 비번 세팅
                        userRegDTO.loginPw = BCryptPasswordEncoder().encode("${userRegDTO.snsUserId}")
                    }
        } else {
            userRegDTO.loginPw
                    .throwNullOrEmpty { FieldErrorException(userRegDTO::loginPw.name, "{ex.need}", "{word.loginPw}") }
                    .let {
                        checkPassword(it)
                        // email 경우 snsUserId 에 email 세팅
                        userRegDTO.snsUserId = userRegDTO.email
                        // 비번암호화
                        userRegDTO.loginPw = BCryptPasswordEncoder().encode(it)
                    }
        }

        if (!userRegDTO.agreePolicy) {
            throw FieldErrorException(userRegDTO::agreePolicy.name, "{ex.need_to.agree_policy}")
        }

        userRegDTO.realName
                .throwNullOrEmpty { FieldErrorException(userRegDTO::realName.name, "{ex.need}", "{word.realName}") }

        return ResponseResult(userService.saveFrom(userRegDTO))
    }

    @GetMapping(value = ["/email"])
    fun userByEmail(@RequestParam email: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findByEmail(email) ?: throw CommonException("잘못된 이메일입니다 "))
    }

    @GetMapping(value = ["/me"])
    fun getMe(authentication: Authentication, httpServletRequest: HttpServletRequest): ResponseResult {
        val userInfo = authentication.principal as CustomJwtUserInfo
        return ResponseResult(userService.findByEmail(userInfo.username))
    }

    @GetMapping(value = [""])
    fun getUserList(principal: Principal): ResponseResult {
        // TODO
        val list = userService.findAll()
        return ResponseResult(list = list, total = list.size.toLong(), length = 2, current = 3)
    }

    @PostMapping(value = ["/login/email"])
    fun emailLogin(
        @RequestBody @Valid emailLoginDTO: EmailLoginDTO,
        errors: Errors
    ): ResponseResult {

        return ResponseResult(userService.emailLogin(emailLoginDTO))
    }

    /***
     * 1. token 에서 access_token , sns_type 등 체크
     * 2. 해당 auth server 에 info 체크
     * 3. 일치 시 이미 해당 정보 멤버 있는지 확인
     * 4. 없으면 없다는 response 로 등록 과정 유도
     * 5. 있으면 로그인 시키고 jwtToken 내려줌
     */
    @PostMapping(value = ["/login/sns"])
    fun checkUserInfoByAccessToken(
        @RequestBody snsTokenDTO: SnsTokenDTO,
        errors: Errors
    ): ResponseResult {
        // TODO 필드가 null 이거나 맞지 않는 타입일 때 아무런 메시지 없이 400 에러 발생함.
        if (errors.hasErrors()) {
            throw FieldErrorException(errors)
        }
        // 해당 auth server 에 info 체크
        if (!userService.checkValidUserInfo(snsTokenDTO)) {
            throw CommonException("인증정보가 일치하지 않습니다.")
        }

        // 가입되어 있는지 체크
        val user = userService.findBySnsTypeAndSnsUserId(snsTokenDTO.snsType, snsTokenDTO.snsUserId)
                ?: return ResponseResult("go_to_register")

        // 로그인 처리
        return ResponseResult(userService.snsLogin(snsTokenDTO, user))
    }

    /**
     * 이미 가입되어 있다는 메시지와 함께, 어떤 SNS 로 가입했는지 예외 발생
     */
    fun throwAlreadyRegistered(registeredList: List<String>) {
        throw CommonException(
                registeredList,
                EX_ALREADY_REGISTERED
        )
    }

    fun checkRegisteredEmail(email: String) {
        userService.findByEmail(email) ?:  throwAlreadyRegistered(listOf())
            throwAlreadyRegistered(listOf()) // TODO
        }
    }

    fun checkRegisteredUid(snsType: SnsType, snsUserId: String) {
        userService.findBySnsTypeAndSnsUserId(snsType, snsUserId)?.let {
            throwAlreadyRegistered(listOf(it.snsType.toString()))
        }
    }

    fun checkPassword(password: String) {
        if (!passwordRegex.containsMatchIn(password)) {
            throw CommonException("패스워드 규칙에 맞춰주세요. 영문+숫자 혼합된 8자 이상")
        }
    }
}
