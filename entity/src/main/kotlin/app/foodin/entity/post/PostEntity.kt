package app.foodin.entity.post

import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostUserDto
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.common.converter.ListToCsvConverter
import app.foodin.entity.user.UserEntity
import org.modelmapper.ModelMapper
import javax.persistence.*

@Entity
@Table(name = "posts")
data class PostEntity(
    var name: String
) : StatusEntity<Post>() {

    @ManyToOne
    @JoinColumn(name = "write_user_id")
    var writeUserEntity: UserEntity? = null

    var contents: String? = null

    var mainImageUri: String? = null

    @Column(name = "image_uris", columnDefinition = "TEXT")
    @Convert(converter = ListToCsvConverter::class)
    var imageUriList: List<String> = listOf()

    var tags: String? = null

    var loveCount: Int = 0

    var commentCount: Int = 0

    constructor(post: Post) : this(post.name) {
        setBaseFieldsFromDomain(post)
        writeUserEntity = post.writeUser?.let { UserEntity(it) }
        contents = post.contents
        tags = post.tagList.listToTags()
        mainImageUri = post.mainImageUri
        imageUriList = post.imageUriList
        loveCount = post.loveCount
        commentCount = post.commentCount
        status = post.status
    }

    override fun toDomain(): Post {
        return Post(name = this.name).also {
            setDomainBaseFieldsFromEntity(it)
            it.writeUser = this.writeUserEntity?.toDomain()
            it.writeUserId = this.writeUserEntity?.id
            it.contents = this.contents
            it.tagList = this.tags.tagsToList()
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUriList
            it.loveCount = this.loveCount
            it.commentCount = this.commentCount
            it.writeUser = this.writeUserEntity?.toDomain()
            it.status = this.status
        }
    }

    fun toDto(): PostUserDto {
        return ModelMapper().map(this, PostUserDto::class.java).also {
            it.writeUserId = this.writeUserEntity?.id
            it.writeUserNickName = this.writeUserEntity?.nickName
        }
    }
}
