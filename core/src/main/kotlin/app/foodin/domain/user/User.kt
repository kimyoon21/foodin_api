package app.foodin.domain.user

import app.foodin.common.enums.AuthRole
import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import app.foodin.common.extension.csvToList
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated

class User(
        val email: String,
        val name: String,
        val snsType: SnsType

) : UserDetails {

    var id : Long? = null

    var loginPw: String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var birthFullDay: LocalDate? = null

    var birthYear: Int? = null

    @Enumerated(EnumType.STRING)
    var sex: Sex? = null

    var nickName: String? = null

    var authRole: AuthRole = AuthRole.USER

    var firstReviewTime: Timestamp? = null

    var phoneNumber: String? = null

    var phoneCountryCode: String? = null

    var reviewCount: Int? = 0

    var findCount: Int? = 0

    var recipeCount: Int? = 0

    var followingCount: Int? = 0

    var followerCount: Int? = 0

    /*** for UserDetails */
    // auth csv
    var authoritiesStr: String? = null

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

    override fun getUsername() = email

    override fun getPassword() = loginPw

    override fun isEnabled() = enabled

    override fun isCredentialsNonExpired() = !credentialsExpired

    override fun isAccountNonExpired() = !accountExpired

    override fun isAccountNonLocked() = !accountLocked

    override fun getAuthorities() = authoritiesStr?.csvToList()?.map { Authority(it) } ?: mutableListOf()
}

