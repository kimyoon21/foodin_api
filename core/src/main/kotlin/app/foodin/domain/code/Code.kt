package app.foodin.domain.code

import app.foodin.domain.common.BaseDomain

data class Code(
    override var id: Long = 0,
    var code: String,
    var codeName: String,
    var codeType: String

) : BaseDomain(id) {
    var imageUri: String? = null
    var infoJson: String? = null
    var seq : Int = Integer.MAX_VALUE
}