package app.foodin.entity.user

import app.foodin.common.enums.AuthRole
import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import app.foodin.entity.common.BaseEntity
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "user")
data class UserEntity(
    var email: String,
    var realName: String,
    @Enumerated(EnumType.STRING)
    var snsType: SnsType

) : BaseEntity<User>() {

    var snsUserId: String? = null

    var loginPw: String? = null

    var birth: Birth? = null

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

    /*** for UserDetails */

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var authoritiesStr: String? = AuthRole.ROLE_USER.name

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

    constructor(user: User) : this(user.email, user.realName, user.snsType) {
        snsUserId = user.snsUserId
        loginPw = user.loginPw
        birth = Birth(user.birthFullDay)
        gender = user.gender
        nickName = user.nickName
        profileImageUri = user.profileImageUri
        firstFoodRegTime = user.firstFoodRegTime
        firstReviewRegTime = user.firstReviewRegTime
        firstRecipeRegTime = user.firstRecipeRegTime
        phoneNumber = user.phoneNumber
        phoneCountryCode = user.phoneCountryCode
        reviewCount = user.reviewCount
        findCount = user.findCount
        recipeCount = user.recipeCount
        followingCount = user.followingCount
        followerCount = user.followerCount
        mainBadgeId = user.mainBadgeId

        agreePolicy = user.agreePolicy
        agreeMarketing = user.agreeMarketing
        authoritiesStr = user.authoritiesStr
        enabled = user.enabled
        credentialsExpired = user.credentialsExpired
        accountExpired = user.accountExpired
        accountLocked = user.accountLocked
    }

    override fun toDomain(): User {
        return User(email = this.email, realName = this.realName, snsType = this.snsType).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.snsUserId = this.snsUserId
            it.loginPw = this.loginPw
            it.birthFullDay = this.birth?.birthFullDay
            it.birthYear = this.birth?.birthYear
            it.gender = this.gender
            it.nickName = this.nickName
            it.profileImageUri = this.profileImageUri
            it.firstFoodRegTime = this.firstFoodRegTime
            it.firstReviewRegTime = this.firstReviewRegTime
            it.firstRecipeRegTime = this.firstRecipeRegTime
            it.phoneNumber = this.phoneNumber
            it.phoneCountryCode = this.phoneCountryCode
            it.reviewCount = this.reviewCount
            it.findCount = this.findCount
            it.recipeCount = this.recipeCount
            it.followingCount = this.followingCount
            it.followerCount = this.followerCount
            it.mainBadgeId = this.mainBadgeId

            it.agreePolicy = this.agreePolicy
            it.agreeMarketing = this.agreeMarketing
            it.authoritiesStr = this.authoritiesStr
            it.enabled = this.enabled
            it.credentialsExpired = this.credentialsExpired
            it.accountExpired = this.accountExpired
            it.accountLocked = this.accountLocked
        }
    }
}