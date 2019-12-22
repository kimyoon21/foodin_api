package app.foodin.domain.food

class FoodFoundUserReq(
    var foodId: Long,
    var sellerId: Long
){
    var categoryId : Long? = null
}