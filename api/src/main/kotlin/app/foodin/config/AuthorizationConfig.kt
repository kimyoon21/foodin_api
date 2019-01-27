package app.foodin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import javax.sql.DataSource

/**
 * Authorization Server
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationConfig(
        val dataSource: DataSource,
        val authenticationManager: AuthenticationManager
) : AuthorizationServerConfigurerAdapter() {

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(SecurityConstants.SECRET)
//        val keyPair = KeyStoreKeyFactory(ClassPathResource("server.jks"), "passtwo".toCharArray())
//                .getKeyPair("auth", "passone".toCharArray())
//        converter.setKeyPair(keyPair)
        return converter
    }

    @Bean
    @Primary
    fun tokenService(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(jwtTokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }

    @Bean
    fun jwtTokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    //    @Bean
//    fun jdbcTokenStore(): TokenStore {
//        return JdbcTokenStore(dataSource)
//    }
    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
    }

    @Bean
    @Primary
    fun jdbcClientDetailsService(dataSource: DataSource): JdbcClientDetailsService {
        return JdbcClientDetailsService(dataSource)
    }


    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(jdbcClientDetailsService(dataSource))
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(jwtTokenStore()).authenticationManager(authenticationManager)
    }
}
