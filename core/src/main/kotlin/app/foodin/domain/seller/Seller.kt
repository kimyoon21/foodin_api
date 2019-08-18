package app.foodin.domain.seller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.domain.common.BaseDomain
import app.foodin.domain.review.ReviewReq

data class Seller(
    override var id: Long = 0,
    var name: String
) : BaseDomain(id) {

    var summary : String? = null
    var iconImageUri : String? = null
    var foodCount: Int = 0
    var spotCount: Int = 0
    var profileImageUri: String? = null

    override fun setFromRequest(request: Any) {
        if(request is SellerReq){
            request.let {
                this.name = it.name
                this.summary = it.summary
                this.iconImageUri = it.iconImageUri
            }
        }else{
            throw CommonException(EX_INVALID_REQUEST)
        }
    }
}
