package app.foodin.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

/**
 * Resource Server
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
//        http.headers().frameOptions().disable()
        http.authorizeRequests().antMatchers("/product/**").authenticated()
                .anyRequest().permitAll()
////                .antMatchers("/user","/authorization-code-test").access("#oauth2.hasScope('read')")
//                .anyRequest().permitAll()
    }
}
