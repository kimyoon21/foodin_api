package app.foodin.common.result

import org.springframework.data.domain.Page

class PagingData<T>() {

    var list: List<T> = listOf()
    var totalCount: Long = 0
    var totalPageCount: Int = 0
    var size: Int = 0
    /*** page No. : Pageable 과 동일하게 page 로 맞춰줌 ***/
    var page: Int = 0
    var hasNext: Boolean = false

    constructor(page: Page<T>) : this() {
        this.list = page.toList()
        this.totalCount = page.totalElements
        this.totalPageCount = page.totalPages
        this.size = page.size
        this.page = page.number
        this.hasNext = page.hasNext()
    }

    constructor(list: List<T>, totalCount: Long, size: Int = list.size, pageNo: Int = 0) : this() {
        this.list = list
        this.totalCount = totalCount
        this.totalPageCount = (totalCount / size).toInt() + 1
        this.size = size
        this.page = pageNo
        this.hasNext = totalPageCount - 1 > page
    }
}
