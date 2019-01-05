package app.foodin.controller

import app.foodin.domain.User
import app.foodin.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
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