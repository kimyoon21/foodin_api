package app.foodin.entity.recipe

import app.foodin.common.extension.hasValueLet
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeFilter
import app.foodin.entity.common.*
import app.foodin.entity.food.FoodEntity
import org.springframework.data.jpa.domain.Specification

class RecipeFilterQuery(val filter: RecipeFilter) : BaseFilterQuery<Recipe, RecipeEntity> {
    override fun toSpecification(): Specification<RecipeEntity> =
            filter.let { filter ->
                and(
                        equalFilter(RecipeEntity::status, filter.status),
                        filter.query.hasValueLet { q ->
                            querysToSpecification(
                                    likeFilter(RecipeEntity::name, q, MatchMode.ANYWHERE),
                                    likeFilter(RecipeEntity::contents, q, MatchMode.ANYWHERE)
                            )
                        },
                        // 푸드카테고리도 리뷰에 적용
                        filterIfHasValue(filter.categoryIdList, where { it.join(RecipeEntity::foodEntityList).get(FoodEntity::categoryId).`in`(filter.categoryIdList) }),
                        isNotNullFilter(RecipeEntity::mainImageUri, filter.hasImage),
                        filterIfHasValue(filter.foodId, where { equal(it.join(RecipeEntity::foodEntityList).get(FoodEntity::id), filter.foodId) })

                )
            }
}
