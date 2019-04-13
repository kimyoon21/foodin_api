package app.foodin.core.service

import app.foodin.core.gateway.FoodLoveGateway
import app.foodin.domain.BaseFilter
import app.foodin.domain.foodLove.FoodLove
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FoodLoveService(override val gateway: FoodLoveGateway) : BaseService<FoodLove,
        BaseFilter>()
