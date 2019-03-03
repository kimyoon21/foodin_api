package app.foodin.domain.seller

import app.foodin.domain.common.BaseDomain

data class Seller(
        override var id : Long = 0,
        var name: String
) : BaseDomain(id){

    var foodCount: Int = 0
    var spotCount: Int = 0
    var profileImageUri: String? = null

}
