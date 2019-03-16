package app.foodin.domain

data class ImageInfo(
    var uri : String?
){
    constructor(uri:String?, width:Int, height:Int, ext:String?, sizeKb:Int?) : this(uri = uri){
        this.width = width
        this.height = height
        this.ext = ext
        this.sizeKb = sizeKb
    }

    var width: Int? = null
    var height: Int?  = null
    var ext : String?  = null
    var sizeKb : Int?  = null
}