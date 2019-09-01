package app.foodin.core.gateway

import app.foodin.domain.notice.Notice
import app.foodin.domain.notice.NoticeFilter

interface NoticeGateway : BaseGateway<Notice, NoticeFilter>
