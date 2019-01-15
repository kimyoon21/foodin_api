package app.foodin.controller

import app.foodin.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController{

    private val userService: UserService

    constructor(userService: UserService) {
        this.userService = userService
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun productAll(): ResponseEntity<Product> {

        SecurityContextHolder.getContext().authentication

        return ResponseEntity.ok(Product("test"))
    }

    @PostMapping
    fun productReg(product : Product) : ResponseEntity<Product>{
        return ResponseEntity.ok(Product("test"))
    }

    class Product(val name : String){

    }
}