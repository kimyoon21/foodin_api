package app.foodin.domain.recipeComment

import app.foodin.common.enums.Status
import app.foodin.domain.comment.BaseComment
import app.foodin.domain.comment.CommentReq
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.user.User

data class RecipeComment(
    override var id: Long = 0,
    var recipeId: Long
) : BaseComment<Recipe>(id, recipeId) {

    var recipe: Recipe? = null

    var recipeWriteUserId: Long? = null

    var hasLoved: Boolean = false

    constructor(recipe: Recipe, writer: User, commentReq: CommentReq) :
            this(recipeId = recipe.id) {
        setFromRequestDTO(commentReq)
        this.writeUserId = writer.id
        this.writeUser = writer
        this.recipe = recipe
        this.recipeWriteUserId = recipe.writeUserId
        this.status = Status.APPROVED
    }
}
