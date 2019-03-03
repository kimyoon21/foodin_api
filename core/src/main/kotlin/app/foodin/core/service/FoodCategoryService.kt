package app.foodin.domain.user

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.domain.foodCategory.FoodCategory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FoodCategoryService(
        private val foodCategoryGateway: FoodCategoryGateway
) : BaseService<FoodCategory>(foodCategoryGateway) {

    private val logger = LoggerFactory.getLogger(FoodCategoryService::class.java)

}