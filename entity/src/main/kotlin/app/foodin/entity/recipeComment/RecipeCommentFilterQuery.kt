package app.foodin.entity.recipeComment

import app.foodin.common.extension.hasIdLet
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class RecipeCommentFilterQuery(val filter: CommentFilter) : BaseFilterQuery<RecipeComment,
        RecipeCommentEntity> {
    override fun toSpecification(): Specification<RecipeCommentEntity> = filter.let {
        and(
                likeFilter(RecipeCommentEntity::contents, it.contents, MatchMode.ANYWHERE),
                it.parentId.hasIdLet {
                    x -> equalFilter(RecipeCommentEntity::parentId, x)
                },
                equalFilter(RecipeCommentEntity::writeUserId, it.writeUserId)
        ) }
}
