package app.foodin.domain.common

import org.springframework.security.core.GrantedAuthority

class Authority(
    private val authority: String
) : GrantedAuthority {
    override fun getAuthority() = authority
}