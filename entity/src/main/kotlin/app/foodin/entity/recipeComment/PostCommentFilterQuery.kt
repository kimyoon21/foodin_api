package app.foodin.entity.recipeComment

import app.foodin.domain.recipeComment.PostCommentFilter
import app.foodin.domain.recipeComment.PostComment
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class PostCommentFilterQuery(val filter: PostCommentFilter) : BaseFilterQuery<PostComment,
        PostCommentEntity> {
    override fun toSpecification(): Specification<PostCommentEntity> = filter.let {
        and(
                likeFilter(PostCommentEntity::contents, it.contents, MatchMode.ANYWHERE),
                it.postId.hasIdLet {
                    x -> equalFilter(PostCommentEntity::postId, x)
                },
                equalFilter(PostCommentEntity::writeUserId, it.writeUserId)
        ) }
}
