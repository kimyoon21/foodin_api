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
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource




/**
 * Authorization Server
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationConfig (
        val dataSource : DataSource,
        val authenticationManager: AuthenticationManager
): AuthorizationServerConfigurerAdapter() {

    private val CLIENT_ID = "foo"
    val CLIENT_SECRET = "{noop}bar"
    private val GRANT_TYPE_PASSWORD = "password"
    private val AUTHORIZATION_CODE = "authorization_code"
    private val REFRESH_TOKEN = "refresh_token"
    private val SCOPE_READ = "read"
    private val SCOPE_WRITE = "write"
    private val TRUST = "trust"
    private val VALID_FOREVER = -1

    @Bean
    fun jdbcTokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
    }
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
//                .inMemory()
//                .withClient(CLIENT_ID)
//                .secret(CLIENT_SECRET)
//                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, "client_credentials")
//                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
//                .accessTokenValiditySeconds(VALID_FOREVER)
//                .refreshTokenValiditySeconds(VALID_FOREVER)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(jdbcTokenStore()).authenticationManager(authenticationManager)
    }
}
