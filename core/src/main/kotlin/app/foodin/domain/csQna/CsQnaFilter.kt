package app.foodin.domain.csQna

import app.foodin.domain.BaseFilter

data class CsQnaFilter(
    val writerUserId: Long? = null,
    val notAnswered: Boolean? = null
) : BaseFilter()
