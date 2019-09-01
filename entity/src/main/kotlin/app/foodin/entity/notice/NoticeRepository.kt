package app.foodin.entity.notice

import app.foodin.core.gateway.NoticeGateway
import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeFilter
import app.foodin.entity.common.BaseRepository
import app.foodin.entity.common.BaseRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : BaseRepositoryInterface<NoticeEntity>

@Component
class JpaNoticeRepository(private val repository: NoticeRepository) : BaseRepository<Notice,
        NoticeEntity, NoticeFilter>(repository), NoticeGateway {

    override fun findAllByFilter(filter: NoticeFilter, pageable: Pageable): Page<Notice> {
                return findAll(NoticeFilterQuery(filter), pageable)
    }

    override fun saveFrom(notice: Notice): Notice {
                return repository.saveAndFlush(NoticeEntity(notice)).toDomain()
    }
}
