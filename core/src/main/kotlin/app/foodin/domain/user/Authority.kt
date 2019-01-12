package app.foodin.domain.user

import org.springframework.security.core.GrantedAuthority

class Authority (
        private val authority: String
): GrantedAuthority {
    override fun getAuthority() = authority
}