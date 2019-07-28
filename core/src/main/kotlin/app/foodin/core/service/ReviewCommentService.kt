package app.foodin.core.service

import app.foodin.core.gateway.ReviewCommentGateway
import app.foodin.domain.comment.CommentCreateReq
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.review.ReviewComment
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewCommentService(
        override val gateway: ReviewCommentGateway,
        private val reviewService: ReviewService,
        private val userService: UserService
) : BaseService<ReviewComment, CommentFilter>(), UserWritableInterface {

    private val logger = LoggerFactory.getLogger(ReviewCommentService::class.java)

    fun save(createReq: CommentCreateReq): ReviewComment {
        // 기 데이터 확인
        // 이미 작성한 리뷰(평점)이 있으면 무조건 update
        val review = reviewService.findById(createReq.parentId)

        val writer = userService.findById(createReq.writeUserId)

        return saveFrom(ReviewComment(review, writer, createReq.commentReq))
    }
}