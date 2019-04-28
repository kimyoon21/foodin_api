package app.foodin.core.service

import app.foodin.core.gateway.RecipeGateway
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeService(override val gateway: RecipeGateway) : BaseService<Recipe, RecipeFilter>()
