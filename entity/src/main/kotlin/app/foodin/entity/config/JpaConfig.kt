package app.foodin.entity.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["app.foodin.entity.user"])
@EnableJpaRepositories(basePackages = ["app.foodin.entity.user"])
class JpaConfig