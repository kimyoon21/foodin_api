package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController{

    @GetMapping()
    fun test(): ResponseResult {
        return ResponseResult("test")
    }
}