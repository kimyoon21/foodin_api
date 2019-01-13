package app.foodin.api.controller

import app.foodin.core.annotation.Loggable
import app.foodin.core.domain.User
import app.foodin.core.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Loggable(result = true, param = true)
class UserController {

    private val userService: UserService

    constructor(userService: UserService) {
        this.userService = userService
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun userByEmail(@RequestParam email: String): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findByEmail(email))
    }
}