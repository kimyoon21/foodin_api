package app.foodin.core.gateway

import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter

interface RecipeGateway : BaseGateway<Recipe, RecipeFilter>
