package app.foodin.config

import app.foodin.entity.user.JpaUserRepository
import app.foodin.entity.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Meta {
    @Bean
    fun buildJpaUserRepository(userRepository: UserRepository) = JpaUserRepository(userRepository)
}