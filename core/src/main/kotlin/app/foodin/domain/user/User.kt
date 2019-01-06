package app.foodin.domain.user

import app.foodin.common.enums.AuthRole
import app.foodin.common.enums.Sex
import app.foodin.common.enums.SnsType
import java.sql.Timestamp
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class User(
        val email: String,
        val name: String,
        val snsType: SnsType

) {

    var password: String? = null

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
}

