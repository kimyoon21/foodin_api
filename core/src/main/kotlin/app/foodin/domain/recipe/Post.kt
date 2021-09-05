package app.foodin.domain.recipe

import app.foodin.common.enums.Status
import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.domain.recipeComment.Commentable
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritable
import com.fasterxml.jackson.annotation.JsonIgnore

data class Post(
    override var id: Long = 0L,
    val name: String
) : StatusDomain(id), UserWritable, Commentable {
    @JsonIgnore
    override var writeUser: User? = null

    override var writeUserId: Long? = null

    fun getWriteUserInfo() = writeUser?.let { UserInfoDto(it) }

    var contents: String? = null

    var mainImageUri: String? = null

    var imageUriList: List<String> = listOf()

    var tagList: List<String> = listOf()

    var loveCount: Int = 0

    var commentCount: Int = 0

    constructor(writer: User, postReq: PostReq) : this(name = postReq.name) {
        setFromRequest(postReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.status = Status.APPROVED
    }

    override fun setFromRequest(request: Any) {
        if (request is PostReq) {
            request.let {
                this.mainImageUri = it.mainImageUri
                this.imageUriList = it.imageUriList
                this.tagList = it.tagList
                this.contents = it.contents
            }
        } else {
            throw CommonException(EX_INVALID_REQUEST)
        }
    }
}
