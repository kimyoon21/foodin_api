package app.foodin.config

import app.foodin.common.enums.UserAuthority
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Resource Server
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig(
    val accessDeniedHandler: CustomAccessDeniedHandler
) : ResourceServerConfigurerAdapter() {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    override fun configure(http: HttpSecurity) {
        // cors 세팅을 웹시큐리티 말고 여기에 해줘야 먹힘. (아래 cofig 는 안해줘도 bean 이름이 같으면 자동으로 되지만 명시적으로 해줌)
        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                // 순서가 상관있다. 위에 한 룰부터 적용되어서 전체허용을 위에 해두면 아래것들은 무시됨
//                .antMatchers("/product/**").authenticated()
                .antMatchers("/food.html").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/test/**").permitAll() // 테스트로 auth 다 풀어버린
                .antMatchers("/code/**").permitAll() // TODO 임시로
                .antMatchers("/food/**").permitAll() // TODO 임시로
                .antMatchers("/image/**").permitAll() // TODO 임시로
                .antMatchers("/user/login/**", "/user/register").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority(UserAuthority.ROLE_ADMIN.name)
                .antMatchers("/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
    }
}
