package app.foodin.entity.tag

import app.foodin.domain.tag.Tag
import app.foodin.domain.tag.TagFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.MathMode
import app.foodin.entity.common.and
import app.foodin.entity.common.likeFilter
import org.springframework.data.jpa.domain.Specification

class TagFilterQuery(val filter: TagFilter) : BaseFilterQuery<Tag, TagEntity> {
    override fun toSpecification(): Specification<TagEntity> = filter.let { filter ->
        and(
                likeFilter(TagEntity::name, filter.name, MathMode.ANYWHERE)

        )
    }
}
