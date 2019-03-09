package app.foodin.domain.code

import app.foodin.domain.BaseFilter

data class CodeFilter(
    val code: String? = null,
    val codeName: String? = null,
    val codeType: String? = null
) : BaseFilter
