package app.foodin.core.service

import app.foodin.core.gateway.TagGateway
import app.foodin.domain.tag.Tag
import app.foodin.domain.tag.TagFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TagService(override val gateway: TagGateway) : BaseService<Tag, TagFilter>() {

    fun saveFrom(tagList: List<Tag>): List<Tag> {
        val createTagList : MutableList<Tag> = mutableListOf()
        for (tag in tagList) {
            createTagList.add(gateway.saveFrom(tag))
        }
        return createTagList
    }
}