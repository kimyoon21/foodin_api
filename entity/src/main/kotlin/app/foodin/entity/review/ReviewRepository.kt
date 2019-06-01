package app.foodin.entity.review

import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.review.Review
import app.foodin.domain.review.ReviewFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : BaseRepositoryInterface<ReviewEntity> {
    fun findByWriteUserIdAndFoodId(userId: Long, foodId: Long): ReviewEntity?
    fun findAllByWriteUserId(userId: Long): List<ReviewEntity>
}

@Component
class JpaReviewRepository(private val repository: ReviewRepository) :
        BaseRepository<Review, ReviewEntity, ReviewFilter>(repository), ReviewGateway {

    override fun findByWriteUserIdAndFoodId(writeUserId: Long, foodId: Long): Review? {
        return repository.findByWriteUserIdAndFoodId(writeUserId, foodId)?.toDomain()
    }

    override fun findAllByFilter(filter: ReviewFilter, pageable: Pageable): Page<Review> {
        return findAll(ReviewFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: Review): Review {
        return repository.saveAndFlush(ReviewEntity(t)).toDomain()
    }

    override fun getByUserId(userId: Long): List<Review> = repository.findAllByWriteUserId(userId).map(ReviewEntity::toDomain)
}