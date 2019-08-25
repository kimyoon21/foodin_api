package app.foodin.entity.food

import app.foodin.domain.food.FoodFoundUser
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.seller.SellerEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "food_found_users")
data class FoodFoundUserEntity(
        @ManyToOne
        @JoinColumn(name = "food_id")
        var food: FoodEntity,
        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: UserEntity,
        @ManyToOne
        @JoinColumn(name = "seller_id")
        var seller: SellerEntity

) : BaseEntity<FoodFoundUser>() {

    constructor(foodFoundUser: FoodFoundUser) : this(
            FoodEntity(foodFoundUser.food),
            UserEntity(foodFoundUser.user),
            SellerEntity(foodFoundUser.seller)
            )

    override fun toDomain(): FoodFoundUser {
        return FoodFoundUser(food = this.food.toDomain()
                , user = this.user.toDomain()
                , seller = this.seller.toDomain()
        ).also {
            setDomainBaseFieldsFromEntity(it)
        }
    }




}