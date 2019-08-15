package app.foodin.entity.tag

import app.foodin.core.gateway.TagGateway
import app.foodin.domain.tag.Tag
import app.foodin.domain.tag.TagFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : BaseRepositoryInterface<TagEntity>

@Component
class JpaTagRepository(private val repository: TagRepository) : BaseRepository<Tag, TagEntity,
        TagFilter>(repository), TagGateway {

    override fun findAllByFilter(filter: TagFilter, pageable: Pageable): Page<Tag> {
        return findAll(TagFilterQuery(filter), pageable)
    }

    override fun saveFrom(tag: Tag): Tag {
                return repository.saveAndFlush(TagEntity(tag)).toDomain()
    }
}
