package app.foodin.entity.tag

import app.foodin.domain.tag.Tag
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "tag", uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])

data class TagEntity(val name: String) : BaseEntity<Tag>() {

    var useCount = 0

    constructor(tag: Tag) : this(tag.name) {
        setBaseFieldsFrom(tag)
        this.useCount = tag.useCount
    }

    override fun toDomain(): Tag {
        return Tag(id, name).also {
            setDomainBaseFieldsFromEntity(it)
            it.useCount = this.useCount
        }
    }
}
