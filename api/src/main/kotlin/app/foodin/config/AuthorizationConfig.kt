package app.foodin.config

import javax.sql.DataSource
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
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * Authorization Server
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationConfig(
    val dataSource: DataSource,
    val authenticationManager: AuthenticationManager,
    val customJwtTokenEnhancer: CustomJwtTokenEnhancer
) : AuthorizationServerConfigurerAdapter() {

//    @Bean
//    fun accessTokenConverter(): JwtAccessTokenConverter {
//        val converter = JwtAccessTokenConverter()
//        converter.setSigningKey(SecurityConstants.SECRET)
// //        val keyPair = KeyStoreKeyFactory(ClassPathResource("server.jks"), "passtwo".toCharArray())
// //                .getKeyPair("auth", "passone".toCharArray())
// //        converter.setKeyPair(keyPair)
//
//        return converter
//    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val accessTokenConverter = CustomJwtAccessTokenConverter()
        accessTokenConverter.setSigningKey(SecurityConstants.SECRET)
//        val keyPair = KeyStoreKeyFactory(ClassPathResource("server.jks"), "passtwo".toCharArray())
// //                .getKeyPair("auth", "passone".toCharArray())
// //        converter.setKeyPair(keyPair)
        return accessTokenConverter
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
        val enhancerChain = TokenEnhancerChain()

        enhancerChain.setTokenEnhancers(listOf(customJwtTokenEnhancer, accessTokenConverter()))
        endpoints
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(enhancerChain)
//                .accessTokenConverter(accessTokenConverter()) chain 에 넣어서 할필요없음
                .authenticationManager(authenticationManager)
    }
}
