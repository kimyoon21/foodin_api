package app.foodin.core.service

import app.foodin.core.gateway.FoodLoveGateway
import app.foodin.domain.foodLove.Love
import app.foodin.domain.foodLove.LoveFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoveService(override val gateway: FoodLoveGateway) : BaseService<Love,
        LoveFilter>()
