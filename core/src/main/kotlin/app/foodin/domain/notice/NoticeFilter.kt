package app.foodin.domain.notice

import app.foodin.domain.BaseFilter

data class NoticeFilter(
        val type: NoticeType? = null,
        val categoryTag: String? = null) : BaseFilter()
