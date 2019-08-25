package app.foodin.entity.user

import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.common.enums.UserAuthority
import app.foodin.common.utils.DateHelper
import app.foodin.common.utils.isProxyObjectInit
import app.foodin.domain.user.User
import app.foodin.domain.user.UserUpdateReq
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.common.toDomainList
import app.foodin.entity.foodCategory.FoodCategoryEntity
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    var email: String,
    var realName: String,
    @Enumerated(EnumType.STRING)
    var snsType: SnsType

) : BaseEntity<User>() {

    var snsUserId: String? = null

    var loginPw: String? = null
    @Embedded
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinTable(name = "user_food_categories",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "food_category_id", referencedColumnName = "id")])
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    var userFoodCategoryEntityList: MutableList<FoodCategoryEntity> = mutableListOf()

    /*** for UserDetails */

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var authoritiesStr: String? = UserAuthority.ROLE_USER.name

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

    constructor(user: User) : this(user.email, user.realName, user.snsType) {
        setBaseFieldsFromDomain(user)
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
        userFoodCategoryEntityList = user.userFoodCategoryList.map { FoodCategoryEntity(it) }.toCollection(LinkedList())

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
            setDomainBaseFieldsFromEntity(it)
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

            if (isProxyObjectInit(this.userFoodCategoryEntityList)) {
                it.userFoodCategoryList = this.userFoodCategoryEntityList.toDomainList()
            }

            it.agreePolicy = this.agreePolicy
            it.agreeMarketing = this.agreeMarketing
            it.authoritiesStr = this.authoritiesStr
            it.enabled = this.enabled
            it.credentialsExpired = this.credentialsExpired
            it.accountExpired = this.accountExpired
            it.accountLocked = this.accountLocked
        }
    }

    fun mergeFromUpdateReq(req: UserUpdateReq) {
        req.let {
            this.realName = it.realName ?: this.realName
            this.nickName = it.nickName
            if (it.loginPw != null && it.loginPw.equals(it.loginPwCheck)) {
                this.loginPw = it.loginPw
            }
            this.gender = it.gender
            val birthFullDay = it.birthday?.let { day -> DateHelper.parse(day) }
            this.birth = Birth(birthFullDay)
            this.profileImageUri = it.profileImageUri
        }
    }
}