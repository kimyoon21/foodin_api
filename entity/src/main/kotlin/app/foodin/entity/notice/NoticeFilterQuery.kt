package app.foodin.entity.notice

import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class NoticeFilterQuery(val filter: NoticeFilter) : BaseFilterQuery<Notice, NoticeEntity> {

    override fun toSpecification(): Specification<NoticeEntity> = filter.let {
        and(
                or(likeFilter(NoticeEntity::contents, it.query, MatchMode.ANYWHERE),
                        likeFilter(NoticeEntity::title, it.query, MatchMode.ANYWHERE)),
                equalFilter(NoticeEntity::type,it.type),
                tagFilter(NoticeEntity::categoryTags, it.categoryTag)
        )
    }
}
