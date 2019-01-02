package app.foodin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@SpringBootApplication
class FoodinApiApplication

fun main(args: Array<String>) {
    runApplication<FoodinApiApplication>(*args)
}
