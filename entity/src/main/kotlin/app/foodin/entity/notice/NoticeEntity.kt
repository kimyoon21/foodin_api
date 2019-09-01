package app.foodin.entity.notice

import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeType
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "notice")
data class NoticeEntity(val title: String,
                        val contents: String,
                        @Enumerated(value = EnumType.STRING)
                        val type: NoticeType,
                        val categoryTags: String?) : BaseEntity<Notice>() {

    constructor(notice: Notice) : this(title = notice.title, contents = notice.contents,
            type = notice.type, categoryTags = notice.categoryTags) {
        setBaseFieldsFromDomain(notice)

    }

    override fun toDomain(): Notice = Notice(id,title,contents,type,categoryTags).also {
        setDomainBaseFieldsFromEntity(it)
    }
}
