package app.foodin.domain.recipeComment

import app.foodin.common.enums.Status
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.recipe.Post
import app.foodin.domain.user.User

data class PostComment(
    override var id: Long = 0,
    var postId: Long
) : StatusDomain(id) {

    var post: Post? = null

    var postWriterId: Long? = null

    var postWriter: User? = null

    var hasLoved: Boolean = false

    constructor(post: Post, writer: User, commentReq: CommentReq) :
            this(postId = post.id) {
        setFromRequestDTO(commentReq)
        this.post = post
        this.postWriterId = post.writeUserId
        this.postWriter = writer
        this.status = Status.APPROVED
    }
}
