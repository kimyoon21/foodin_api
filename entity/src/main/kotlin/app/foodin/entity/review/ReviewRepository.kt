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
interface ReviewRepository : BaseRepositoryInterface<ReviewEntity>

@Component
class JpaReviewRepository(private val reviewRepository: ReviewRepository) :
        BaseRepository<Review, ReviewEntity, ReviewFilter>(reviewRepository), ReviewGateway {

    override fun findAllByFilter(filter: ReviewFilter, pageable: Pageable): Page<Review> {
        return findAll(ReviewFilterQuery(filter), pageable)
    }

    override fun saveFrom(t: Review): Review {
        return reviewRepository.saveAndFlush(ReviewEntity(t)).toDomain()
    }
}