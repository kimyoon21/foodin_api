package app.foodin.entity.recipeComment

import app.foodin.common.extension.csvToList
import app.foodin.common.extension.listToCsv
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.entity.comment.BaseCommentEntity
import app.foodin.entity.recipe.RecipeEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "recipe_comment")
@AttributeOverride(name = "parentId", column = Column(name = "recipe_id"))
@AssociationOverride(name = "parent", joinColumns = [JoinColumn(name = "recipe_id", insertable = false, updatable = false)])
data class RecipeCommentEntity(
    override var parentId: Long,
    override var writeUserId: Long
) : BaseCommentEntity<RecipeComment, Recipe, RecipeEntity>(parentId, writeUserId) {

    constructor(recipeComment: RecipeComment) : this(recipeComment.recipeId, recipeComment.writeUserId!!) {
        setBaseFieldsFromDomain(recipeComment)
        writeUserEntity = UserEntity(recipeComment.writeUser!!)
        contents = recipeComment.contents
        imageUris = recipeComment.imageUriList.listToCsv()
        status = recipeComment.status
    }

    override fun toDomain(): RecipeComment {
        return RecipeComment(this.id, parentId).also {
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
