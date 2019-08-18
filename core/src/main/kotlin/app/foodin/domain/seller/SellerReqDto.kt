package app.foodin.domain.seller

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

/***
 * 판매회사 등록용
 */
@KotlinNoArgConstructor
data class SellerCreateReq(
    var sellerReq: SellerReq
) : Serializable

@KotlinNoArgConstructor
data class SellerUpdateReq(
    var sellerReq: SellerReq
) : Serializable

@KotlinNoArgConstructor
data class SellerReq(
    val name: String,
    val iconImageUri: String? = null,
    val summary: String? = null
)