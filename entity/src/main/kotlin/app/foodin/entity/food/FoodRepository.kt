package app.foodin.entity.food

import app.foodin.core.gateway.FoodGateway
import app.foodin.domain.food.Food
import app.foodin.domain.food.FoodFilter
import app.foodin.domain.food.FoodInfoDto
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : BaseRepositoryInterface<FoodEntity> {
    /***
     * repository 의 메소드의 결과값을 FoodEntity 가 아닌 Food 로 실수로 세팅했는데
     * Food 의 기본생성자 값들로만 컬럼을 필터해서 적용된다. 뭐지?
     * 이걸 활용하면 DTO 를 굳이 toFood 등을 안써서도 가져와볼 수 있을 것으로 보임.
     * 일단 필드는 기본생성자 안에 있는것들만 가져오므로 제약이 많긴 하다.
     * 체크해보니 Return 객체의 생성자 필드 중에서 Entity 객체랑 이름이 동일한걸 자동으로 가져오는것으로 보인다.
     * 흠 편하긴 한데, 명시적으로 DTO 화 하는게 아니라서, Entity 필드 이름이 바뀌면 값을 못읽게 되는 side effect
     *
     */
    fun findByName(name: String): FoodEntity?

    @Modifying
    @Query("UPDATE FoodEntity f set f.loveCount = f.loveCount + 1 where f.id = :id")
    fun addLoveCount(@Param("id") id: Long)
    @Modifying
    @Query("UPDATE FoodEntity f set f.ratingCount = f.ratingCount + 1 where f.id = :id")
    fun addRatingCount(@Param("id") id: Long)
    @Modifying
    @Query("UPDATE FoodEntity f set f.reviewCount = f.reviewCount + 1 where f.id = :id")
    fun addReviewCount(@Param("id") id: Long)
}

@Component
class JpaFoodRepository(private val foodRepository: FoodRepository) :
        BaseRepository<Food, FoodEntity, FoodFilter>(foodRepository), FoodGateway {

    override fun addLoveCount(id: Long) {
        foodRepository.addLoveCount(id)
    }

    override fun addRatingCount(id: Long) {
        foodRepository.addRatingCount(id)
    }

    override fun addReviewCount(id: Long) {
        foodRepository.addReviewCount(id)
    }

    override fun findDtoBy(filter: FoodFilter, pageable: Pageable): Page<FoodInfoDto>? {
        return foodRepository.findAll(FoodFilterQuery(filter).toSpecification(), pageable).map { e -> e.toDto() }
    }

    override fun findAllByFilter(filter: FoodFilter, pageable: Pageable): Page<Food> {
        return findAll(FoodFilterQuery(filter), pageable)
    }

    override fun findByName(name: String): Food? {
        return foodRepository.findByName(name)?.toDomain()
    }

    override fun saveFrom(t: Food): Food {
        return foodRepository.saveAndFlush(FoodEntity(t)).toDomain()
    }
}