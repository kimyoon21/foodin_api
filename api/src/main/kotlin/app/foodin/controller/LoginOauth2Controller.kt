package app.foodin.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping("")
@RestController
class LoginOauth2Controller {
    private val logger = LoggerFactory.getLogger(LoginOauth2Controller::class.java)


    internal inner class TestController {
        @RequestMapping("authorization-code")
        @ResponseBody
        fun authorizationCodeTest(@RequestParam("code") code: String): String {
            return String.format("curl " +
                    "-F \"grant_type=authorization_code\" " +
                    "-F \"code=%s\" " +
                    "-F \"scope=read\" " +
                    "-F \"client_id=foodin\" " +
                    "-F \"client_secret=foodin\" " +
                    "-F \"redirect_uri=http://localhost:8080/test/authorization-code\" " +
                    "\"http://foodin:foodin@localhost:8080/oauth/token\"", code)
        }
    }
}
