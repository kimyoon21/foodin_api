package app.foodin.core.service

import app.foodin.core.gateway.RecipeCommentGateway
import app.foodin.domain.recipeComment.RecipeComment
import app.foodin.domain.recipeComment.RecipeCommentFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeCommentService(override val gateway: RecipeCommentGateway) : BaseService<RecipeComment,
        RecipeCommentFilter>()
