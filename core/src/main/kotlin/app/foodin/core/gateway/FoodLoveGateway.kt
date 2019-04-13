package app.foodin.core.gateway

import app.foodin.domain.foodLove.FoodLove
import app.foodin.domain.foodLove.FoodLoveFilter

interface FoodLoveGateway : BaseGateway<FoodLove, FoodLoveFilter>
