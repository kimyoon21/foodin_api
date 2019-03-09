package app.foodin.core.service

import app.foodin.core.gateway.FoodCategoryGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.foodCategory.FoodCategory
import app.foodin.domain.user.BaseService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FoodCategoryService(
    private val foodCategoryGateway: FoodCategoryGateway
) : BaseService<FoodCategory, BaseFilter>(foodCategoryGateway) {

    private val logger = LoggerFactory.getLogger(FoodCategoryService::class.java)
}