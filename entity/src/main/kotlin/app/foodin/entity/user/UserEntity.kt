package app.foodin.entity.user

import app.foodin.common.enums.AuthRole
import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "user")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var email: Email,
        var name: String,
        @Enumerated(EnumType.STRING)
        var snsType: SnsType


) {

    constructor(user: User) : this(null, Email(user.email), user.name, user.snsType) {
        snsUserId = user.snsUserId
        loginPw = user.loginPw
        birthFullDay = user.birthFullDay
        birthYear = user.birthYear
        gender = user.gender
        nickName = user.nickName
        firstReviewTime = user.firstReviewTime
        phoneNumber = user.phoneNumber
        phoneCountryCode = user.phoneCountryCode
        reviewCount = user.reviewCount
        findCount = user.findCount
        recipeCount = user.recipeCount
        followingCount = user.followingCount
        followerCount = user.followerCount
        agreePolicy = user.agreePolicy
        agreeMarketing = user.agreeMarketing
        authoritiesStr = user.authoritiesStr
        enabled = user.enabled
        credentialsExpired = user.credentialsExpired
        accountExpired = user.accountExpired
        accountLocked = user.accountLocked
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdTime: Timestamp? = null

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedTime: Timestamp? = null

    var snsUserId: String? = null

    var loginPw: String? = null

    var birthFullDay: LocalDate? = null

    var birthYear: Int? = null

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    var nickName: String? = null

    var firstReviewTime: Timestamp? = null

    var phoneNumber: String? = null

    var phoneCountryCode: String? = null

    var reviewCount: Int? = 0

    var findCount: Int? = 0

    var recipeCount: Int? = 0

    var followingCount: Int? = 0

    var followerCount: Int? = 0

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var authoritiesStr: String? = AuthRole.ROLE_USER.name

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

}

fun UserEntity.toUser(): User {
    return User(email = this.email.value, name = this.name, snsType = this.snsType).also {
        it.id = this.id
        it.createdTime = this.createdTime
        it.snsUserId = this.snsUserId
        it.loginPw = this.loginPw
        it.birthFullDay = this.birthFullDay
        it.birthYear = this.birthYear
        it.gender = this.gender
        it.nickName = this.nickName
        it.firstReviewTime = this.firstReviewTime
        it.phoneNumber = this.phoneNumber
        it.phoneCountryCode = this.phoneCountryCode
        it.reviewCount = this.reviewCount
        it.findCount = this.findCount
        it.recipeCount = this.recipeCount
        it.followingCount = this.followingCount
        it.followerCount = this.followerCount
        it.agreePolicy = this.agreePolicy
        it.agreeMarketing = this.agreeMarketing
        it.authoritiesStr = this.authoritiesStr
        it.enabled = this.enabled
        it.credentialsExpired = this.credentialsExpired
        it.accountExpired = this.accountExpired
        it.accountLocked = this.accountLocked
    }
}