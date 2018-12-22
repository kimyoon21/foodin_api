package app.foodin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodinApiApplication

fun main(args: Array<String>) {
    runApplication<FoodinApiApplication>(*args)
}
