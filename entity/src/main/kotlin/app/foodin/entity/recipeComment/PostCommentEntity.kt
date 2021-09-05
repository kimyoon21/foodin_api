package app.foodin.entity.recipeComment

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.domain.recipeComment.PostComment
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.post.PostEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "post_comment")
data class PostCommentEntity(
    @JoinColumn(name = "parent_id")
    open var postId: Long,
    @Column(name = "write_user_id")
    open var writeUserId: Long
) : StatusEntity<PostComment>() {

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var post: PostEntity

    var loveCount: Int = 0

    @ManyToOne
    @JoinColumn(name = "write_user_id", insertable = false, updatable = false)
    lateinit var writeUserEntity: UserEntity

    var contents: String? = null

    @Column(columnDefinition = "TEXT")
    var imageUris: String? = null

    constructor(postComment: PostComment) : this(postComment.recipeId, postComment.writeUserId!!) {
        setBaseFieldsFromDomain(postComment)
        writeUserEntity = UserEntity(postComment.writeUser!!)
        contents = postComment.contents
        imageUris = postComment.imageUriList.listToCsv()
        status = postComment.status
    }

    override fun toDomain(): PostComment {
        return PostComment(this.id, postId).also {
            setDomainBaseFieldsFromEntity(it)
            it.contents = this.contents
            it.imageUriList = this.imageUris.csvToList()
            it.writeUser = this.writeUserEntity.toDomain()
            it.writeUserId = this.writeUserId
            it.status = this.status
            it.loveCount = this.loveCount
        }
    }
}
