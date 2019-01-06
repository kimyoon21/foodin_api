package app.foodin.entity.user

import app.foodin.common.enums.AuthRole
import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import app.foodin.domain.user.User
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var email: String,
        var name: String,
        @Enumerated(EnumType.STRING)
        var snsType: SnsType


) {

    constructor(user: User) : this(null,user.email, user.name, user.snsType) {
        snsUserId = user.snsUserId
        password = user.password
        birthFullDay = user.birthFullDay
        birthYear = user.birthYear
        sex = user.sex
        nickName = user.nickName
        authRole = user.authRole
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
    }


    var snsUserId: String? = null

    var password: String? = null

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

    var agreePolicy : Boolean = false

    var agreeMarketing : Boolean = false


}

fun UserEntity.toUser(): User {
    return User(email = this.email, name = this.name, snsType = this.snsType).also {
        it.id = this.id
        it.snsUserId = this.snsUserId
        it.password = this.password
        it.birthFullDay = this.birthFullDay
        it.birthYear = this.birthYear
        it.sex = this.sex
        it.nickName = this.nickName
        it.authRole = this.authRole
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
    }
}