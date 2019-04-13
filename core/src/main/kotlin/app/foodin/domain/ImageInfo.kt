package app.foodin.domain

import app.foodin.domain.common.EntityType

data class ImageInfo(
    var uri: String?
) {

    constructor(width: Int, height: Int, ext: String?): this(null) {
        this.width = width
        this.height = height
        this.ext = ext
    }

    var width: Int? = null
    var height: Int? = null
    var ext: String? = null
    var sizeKb: Int? = null
    var type: EntityType? = null
}