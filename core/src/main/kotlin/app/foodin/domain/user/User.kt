package app.foodin.domain.user

import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.common.enums.UserAuthority
import app.foodin.common.extension.csvToList
import app.foodin.domain.badge.Badge
import app.foodin.domain.common.Authority
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.foodCategory.FoodCategory
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated

class User(
    override var id: Long = 0,
    val email: String,
    var realName: String,
    val snsType: SnsType

) : BaseDomain(id), UserDetails {

    /****
     * sns 타입 email 아니면, snsType snsUserId 를 조합해서 생성
     */
    @get:JsonIgnore
    var loginPw: String? = null

    var snsUserId: String? = null

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var birthFullDay: LocalDate? = null

    var birthYear: Int? = null

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    var nickName: String? = null

    var profileImageUri: String? = null

    var firstFoodRegTime: Timestamp? = null

    var firstReviewRegTime: Timestamp? = null

    var firstRecipeRegTime: Timestamp? = null

    var phoneNumber: String? = null

    var phoneCountryCode: String? = null

    var reviewCount: Int? = 0

    var findCount: Int? = 0

    var recipeCount: Int? = 0

    var followingCount: Int? = 0

    var followerCount: Int? = 0

    var mainBadgeId: Long? = null
    //    @JoinColumn TODO
//    @ManyToOne
    var mainBadge: Badge? = null

    var userFoodCategoryList: List<FoodCategory> = listOf()

    var userFoodFilterList: List<String> = listOf()

    /*** for UserDetails */
    // auth csv
    var authoritiesStr: String? = UserAuthority.ROLE_USER.name

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

    override fun getUsername() = email
    @JsonIgnore
    override fun getPassword() = loginPw

    override fun isEnabled() = enabled

    override fun isCredentialsNonExpired() = !credentialsExpired

    override fun isAccountNonExpired() = !accountExpired

    override fun isAccountNonLocked() = !accountLocked

    override fun getAuthorities() = authoritiesStr?.csvToList()?.map { Authority(it) } ?: mutableListOf()

    fun getUserAuthorities() = authoritiesStr?.csvToList()?.map { UserAuthority.valueOf(it) } ?: mutableListOf()
}
