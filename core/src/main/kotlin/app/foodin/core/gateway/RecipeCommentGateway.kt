package app.foodin.core.gateway

import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.domain.recipeComment.RecipeCommentFilter

interface RecipeCommentGateway : BaseGateway<RecipeComment, RecipeCommentFilter>
