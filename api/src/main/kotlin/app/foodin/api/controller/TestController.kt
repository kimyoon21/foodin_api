package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController{

    @RequestMapping(method = [RequestMethod.GET])
    fun test(): ResponseResult {
        return ResponseResult("test")
    }
}