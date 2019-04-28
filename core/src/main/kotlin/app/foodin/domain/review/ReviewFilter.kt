package app.foodin.domain.review

import app.foodin.domain.BaseFilter

data class ReviewFilter(
    val name: String? = null,
    val categoryIdList: List<Long> = listOf(),
    val tag: String? = null,
    val sellerNameList: List<String> = listOf(),
    val foodId: Long? = null
) : BaseFilter()
