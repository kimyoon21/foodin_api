package app.foodin.domain.food

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.seller.Seller
import app.foodin.domain.user.User

class FoodFoundUser(
    override var id: Long = 0,
    var food: Food,
    var user: User,
    var seller: Seller
) : BaseDomain(id)
