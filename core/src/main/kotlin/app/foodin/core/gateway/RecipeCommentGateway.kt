package app.foodin.core.gateway

import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.recipeComment.RecipeComment

interface RecipeCommentGateway : BaseGateway<RecipeComment, CommentFilter> {
    fun addLoveCount(id: Long, count: Int)
}
