package app.foodin.domain.commentLove

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.review.ReviewComment
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import com.fasterxml.jackson.annotation.JsonIgnore

data class CommentLove(
    override var id: Long = 0L,
    val reviewComment: ReviewComment? = null,
//                       val recipeComment: RecipeComment? = null,
    @JsonIgnore
    val user: User
) : BaseDomain(id){

    fun getUserInfo() : UserInfoDto{
        return UserInfoDto(user)
    }
}
