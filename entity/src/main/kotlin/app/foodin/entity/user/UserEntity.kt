package app.foodin.entity.user

import app.foodin.common.enums.Gender
import app.foodin.common.enums.SnsType
import app.foodin.common.enums.UserAuthority
import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.common.utils.DateHelper
import app.foodin.common.utils.isProxyObjectInit
import app.foodin.domain.user.User
import app.foodin.domain.user.UserUpdateReq
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.common.toDomainList
import app.foodin.entity.foodCategory.FoodCategoryEntity
import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "users")
data class UserEntity(
    var loginId: String,
    var realName: String,
    @Enumerated(EnumType.STRING)
    var snsType: SnsType

) : BaseEntity<User>() {

    var email: String? = null

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

    var userFoodFilterStr: String? = null

    /*** for UserDetails */

    var agreePolicy: Boolean = false

    var agreeMarketing: Boolean = false

    var authoritiesStr: String? = UserAuthority.ROLE_USER.name

    var enabled: Boolean = true

    var credentialsExpired: Boolean = false

    var accountExpired: Boolean = false

    var accountLocked: Boolean = false

    constructor(user: User) : this(user.loginId, user.realName, user.snsType) {
        setBaseFieldsFromDomain(user)
        email = user.email
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
        userFoodFilterStr = user.userFoodFilterList.listToCsv()
        agreePolicy = user.agreePolicy
        agreeMarketing = user.agreeMarketing
        authoritiesStr = user.authoritiesStr
        enabled = user.enabled
        credentialsExpired = user.credentialsExpired
        accountExpired = user.accountExpired
        accountLocked = user.accountLocked
    }

    override fun toDomain(): User {
        return User(loginId = this.loginId, realName = this.realName, snsType = this.snsType).also {
            setDomainBaseFieldsFromEntity(it)
            it.email = this.email
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
            it.userFoodFilterList = this.userFoodFilterStr.csvToList()

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
            this.nickName = it.nickName ?: this.nickName
            if (it.loginPw != null && it.loginPw.equals(it.loginPwCheck)) {
                this.loginPw = it.loginPw
            }
            this.gender = it.gender ?: this.gender
            val birthFullDay = it.birthday?.let { day -> DateHelper.parse(day) }
            this.birth = Birth(birthFullDay)
            this.profileImageUri = it.profileImageUri ?: this.profileImageUri
            this.userFoodFilterStr = it.userFoodFilterList.listToCsv()
            this.enabled = it.enabled ?: this.enabled
        }
    }
}
