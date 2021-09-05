package app.foodin.core.gateway

import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.recipeComment.PostComment

interface RecipeCommentGateway : BaseGateway<PostComment, PostCommentFilter> {
    fun addLoveCount(id: Long, count: Int)
}
