package app.foodin.entity.recipeComment

import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.domain.recipeComment.RecipeCommentFilter
import app.foodin.entity.common.BaseFilterQuery
import org.springframework.data.jpa.domain.Specification

class RecipeCommentFilterQuery(val filter: RecipeCommentFilter) : BaseFilterQuery<RecipeComment,
        RecipeCommentEntity> {
    override fun toSpecification(): Specification<RecipeCommentEntity> {
                TODO("not implemented")
    }
}
