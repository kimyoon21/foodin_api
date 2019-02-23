package app.foodin.config

import app.foodin.common.enums.AuthRole
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter


/**
 * Resource Server
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig(
        val accessDeniedHandler: CustomAccessDeniedHandler
) : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
//        http.headers().frameOptions().disable()

        http.authorizeRequests()
                // 순서가 상관있다. 위에 한 룰부터 적용되어서 전체허용을 위에 해두면 아래것들은 무시됨
//                .antMatchers("/product/**").authenticated()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/test/**").permitAll() // 테스트로 auth 다 풀어버린
                .antMatchers("/food/**").permitAll() //TODO 임시로
                .antMatchers("/user/login/**", "/user/register").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority(AuthRole.ROLE_ADMIN.name)
                .antMatchers("/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)

    }
}
