package app.foodin.domain.comment

import app.foodin.common.enums.Status
import app.foodin.domain.common.StatusDomain
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import app.foodin.domain.writable.UserWritable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.modelmapper.ModelMapper
import javax.persistence.FetchType
import javax.persistence.ManyToOne

open class BaseComment<P : Commentable>(
    override var id: Long = 0,
    var parentId: Long
) : StatusDomain(id), UserWritable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private var parent: P? = null

    @JsonIgnore
    override var writeUser: User? = null

    override var writeUserId: Long? = null

    fun getWriteUserInfo() = writeUser?.let { UserInfoDto(it) }

    var contents: String? = null

    var imageUriList: List<String> = listOf()

    var loveCount: Int = 0

    constructor(parent: P, writer: User, commentReq: CommentReq) : this(parentId = parent.id) {
        this.parent = parent
        setFromRequestDTO(commentReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.status = Status.APPROVED
    }

    fun setFromRequestDTO(commentReq: CommentReq) {
        commentReq.let {
            this.imageUriList = it.imageUriList
            this.contents = it.contents
        }
    }

    fun createFromReq(commentCreateReq: CommentCreateReq): BaseComment<out Commentable> {
        return ModelMapper().map(commentCreateReq, BaseComment::class.java).also {
            it.status = Status.APPROVED
        }
    }
}