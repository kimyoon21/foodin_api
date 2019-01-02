package app.foodin.controller

import app.foodin.service.UserService
import org.springframework.http.ResponseEntity
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
        return ResponseEntity.ok(Product("test"))
    }

    class Product(val name : String){

    }
}