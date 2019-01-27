package app.foodin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication(scanBasePackages = ["app.foodin"])
@PropertySources(
    PropertySource(value = "classpath:entity.properties"),
    PropertySource(value = "classpath:entity-\${spring.profiles.active:}.properties", ignoreResourceNotFound = true)
)
class FoodinApiApplication

fun main(args: Array<String>) {
    runApplication<FoodinApiApplication>(*args)
}
