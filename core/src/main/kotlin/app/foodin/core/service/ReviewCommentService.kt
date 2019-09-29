package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.core.gateway.ReviewCommentGateway
import app.foodin.domain.comment.CommentCreateReq
import app.foodin.domain.comment.CommentFilter
import app.foodin.domain.comment.CommentUpdateReq
import app.foodin.domain.review.ReviewComment
import app.foodin.domain.writable.UserWritableInterface
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewCommentService(
    override var gateway: ReviewCommentGateway,
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

    fun update(id: Long, updateReq: CommentUpdateReq): ReviewComment? {
        val reviewComment = gateway.findById(id) ?: throw CommonException(EX_NOT_EXISTS, "댓글")
        reviewComment.setFromRequestDTO(updateReq.commentReq)
        return saveFrom(reviewComment)
    }

    fun addLoveCount(id: Long) {
        gateway.addLoveCount(id)
    }
}