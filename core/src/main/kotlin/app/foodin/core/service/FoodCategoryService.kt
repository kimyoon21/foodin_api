package app.foodin.core.service

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.foodCategory.FoodCategory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FoodCategoryService(
    override val gateway: FoodCategoryGateway
) : BaseService<FoodCategory, BaseFilter>() {

    private val logger = LoggerFactory.getLogger(FoodCategoryService::class.java)
}
