package app.foodin.entity.commentLove

import app.foodin.domain.commentLove.CommentLove
import app.foodin.domain.commentLove.CommentLoveFilter
import app.foodin.entity.common.BaseFilterQuery
import org.springframework.data.jpa.domain.Specification

class CommentLoveFilterQuery(val filter: CommentLoveFilter) : BaseFilterQuery<CommentLove,
        CommentLoveEntity> {
    override fun toSpecification(): Specification<CommentLoveEntity> {
                TODO("not implemented")
    }
}
