package app.foodin.entity.recipeComment

import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import kotlin.String

@Entity
@Table(name = "recipe_comment")
data class RecipeCommentEntity(val field1: String) : BaseEntity<RecipeComment>() {
    constructor(recipeComment: RecipeComment) : this(field1 = recipeComment.field1) {
        setBaseFieldsFromDomain(recipeComment)
    }

    override fun toDomain(): RecipeComment = RecipeComment(id,field1).also {
                setDomainBaseFieldsFromEntity(it)
                TODO("not implemented")
            }}
