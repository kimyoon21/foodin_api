package app.foodin.domain.recipeComment

import app.foodin.domain.BaseFilter

data class PostCommentFilter(
    val contents: String? = null,
    val writeUserId: Long? = null,
    var postId: Long? = null
) : BaseFilter()
