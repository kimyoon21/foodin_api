package app.foodin.config

import app.foodin.domain.user.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        private val customUserDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    // 인증 처리 방법에 대한 설정
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    // 로그인 URL 등록
    override fun configure(http: HttpSecurity) {
        http.oauth2Login()
    }

    /**
     * 데이터베이스 인증용 Provider
     * @return
     */
    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(customUserDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder()) //패스워드를 암호활 경우 사용한다
        return authenticationProvider
    }

    override fun configure(web: WebSecurity) {
        web
                .ignoring()
                // favicon.ico
                .antMatchers("/favicon.ico")
                // Swagger UI 설정
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger/**"
                )
    }
}
