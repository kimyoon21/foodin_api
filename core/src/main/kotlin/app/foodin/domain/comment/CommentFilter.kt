package app.foodin.domain.comment

import app.foodin.domain.BaseFilter

data class CommentFilter(
    val contents: String? = null,
    val writeUserId: Long? = null
) : BaseFilter()
