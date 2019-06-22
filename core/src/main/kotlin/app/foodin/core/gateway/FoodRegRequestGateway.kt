package app.foodin.core.gateway

import app.foodin.domain.foodRegRequest.FoodRegRequest
import app.foodin.domain.foodRegRequest.FoodRegRequestFilter

interface FoodRegRequestGateway : BaseGateway<FoodRegRequest, FoodRegRequestFilter>
