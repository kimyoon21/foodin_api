package app.foodin.core.service

import app.foodin.core.gateway.NoticeGateway
import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NoticeService(override val gateway: NoticeGateway) : BaseService<Notice, NoticeFilter>()
