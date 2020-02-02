package app.foodin.entity.recipe

import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.recipe.Recipe
import app.foodin.domain.recipe.RecipeInfoDto
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.common.converter.ListToCsvConverter
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.user.UserEntity
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.modelmapper.ModelMapper
import javax.persistence.*

@Entity
@Table(name = "recipes")
data class RecipeEntity(
        var name: String) : StatusEntity<Recipe>() {

    @ManyToOne
    @JoinColumn(name = "write_user_id")
    var writeUserEntity: UserEntity? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinTable(name = "recipes_foods", joinColumns = [JoinColumn(name = "recipe_id", nullable = false, updatable = false)], inverseJoinColumns = [JoinColumn(name = "food_id", nullable = false, updatable = false)])
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    var foodEntityList: MutableList<FoodEntity> = mutableListOf()

    var contents: String? = null

    var mainImageUri: String? = null

    @Column(name = "image_uris", columnDefinition = "TEXT")
    @Convert(converter = ListToCsvConverter::class)
    var imageUriList: List<String> = listOf()

    var tags: String? = null

    var loveCount: Int = 0

    var ratingCount: Int = 0

    var reviewCount: Int = 0

    var ratingAvg: Float? = null

    constructor(recipe: Recipe) : this(recipe.name) {
        setBaseFieldsFromDomain(recipe)
        writeUserEntity = recipe.writeUser?.let { UserEntity(it) }
        foodEntityList = recipe.foodList.map { FoodEntity(it)}.toMutableList()
        contents = recipe.contents
        tags = recipe.tagList.listToTags()
        mainImageUri = recipe.mainImageUri
        imageUriList = recipe.imageUriList
        loveCount = recipe.loveCount
        reviewCount = recipe.reviewCount
        ratingCount = recipe.ratingCount
        ratingAvg = recipe.ratingAvg
        status = recipe.status
    }

    override fun toDomain(): Recipe {
        return Recipe(name = this.name).also {
            setDomainBaseFieldsFromEntity(it)
            it.writeUser = this.writeUserEntity?.toDomain()
            it.writeUserId = this.writeUserEntity?.id
            it.foodList = this.foodEntityList.map { foodEntity ->  foodEntity.toDomain() }.toMutableList()
            it.contents = this.contents
            it.tagList = this.tags.tagsToList()
            it.mainImageUri = this.mainImageUri
            it.imageUriList = this.imageUriList
            it.loveCount = this.loveCount
            it.ratingCount = this.ratingCount
            it.reviewCount = this.reviewCount
            it.ratingAvg = this.ratingAvg
            it.writeUser = this.writeUserEntity?.toDomain()
            it.status = this.status

        }
    }

    fun toDto(): RecipeInfoDto {
        return ModelMapper().map(this, RecipeInfoDto::class.java).also {
            it.foodList = this.foodEntityList.map { x -> x.toDto() }
            it.writeUserId = this.writeUserEntity?.id
            it.writeUserNickName = this.writeUserEntity?.nickName
        }
    }
}
