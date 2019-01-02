package app.foodin.controller

import app.foodin.auth.AuthenticationRequest
import app.foodin.user.SnsType
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.FieldErrorException
import app.foodin.common.extension.hasValueOrElseThrow
import app.foodin.common.result.ResponseResult
import app.foodin.domain.User
import app.foodin.service.UserService
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController{

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    private val userService: UserService

    constructor(userService: UserService) {
        this.userService = userService
    }

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
        userService.findByEmail(email).ifPresent { user ->
            throwAlreadyRegistered(listOf()) //TODO
        }
    }

    fun checkRegisteredUid(snsType: SnsType, uid: String) {
        userService.findBySnsTypeAndUid(snsType, uid).ifPresent {
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
            @ApiParam(value = "Authentication Request") @RequestBody @Valid authenticationRequest: AuthenticationRequest,
            @ApiIgnore errors: Errors
    ): ResponseResult {
        logger.info("authRequest: $authenticationRequest")

        if(errors.hasErrors()) {
            throw FieldErrorException(errors)
        }


        authenticationRequest.email
                .hasValueOrElseThrow { FieldErrorException(authenticationRequest::email.name, "{ex.need}", "{word.email}") }
                .let { checkRegisteredEmail(it) }


        if(authenticationRequest.snsType != SnsType.EMAIL) {
            authenticationRequest.uid
                    .hasValueOrElseThrow { FieldErrorException(authenticationRequest::uid.name, "{ex.need}", "{word.uid}") }
                    .let { checkRegisteredUid(authenticationRequest.snsType, it) }
        } else {
            authenticationRequest.password
                    .hasValueOrElseThrow { FieldErrorException(authenticationRequest::password.name, "{ex.need}", "{word.password}") }
                    .let { checkPassword(it) }
        }

        if(!authenticationRequest.agreePolicy) {
            throw FieldErrorException(authenticationRequest::agreePolicy.name, "{ex.need_to.agree_policy}")
        }

        authenticationRequest.name
                .hasValueOrElseThrow { FieldErrorException(authenticationRequest::name.name, "{ex.need}", "{word.name}") }

        return ResponseResult(userService.saveFrom(authenticationRequest))
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun userByEmail(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.findAll())
    }

    @RequestMapping(value = "/email", method = [RequestMethod.GET])
    fun userByEmail(@RequestParam email: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findByEmail(email))
    }
}