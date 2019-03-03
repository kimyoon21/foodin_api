package app.foodin.domain.foodCategory

import app.foodin.domain.common.BaseDomain

data class FoodCategory(
        override var id : Long = 0,
        var detailName: String,
        var filterName: String,
        var groupName: String
) : BaseDomain(id){

    var foodCount: Int = 0

}
