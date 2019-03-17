package app.foodin.common.result

import org.springframework.data.domain.Page

open class ResponseTypePagingResult<T>(
    open var message: String? = "ok",
    open var data: PagingData<T>? = null
) {

    open var succeeded: Boolean = true

    constructor(page: Page<T>) : this(data = PagingData<T>(page))

    constructor(list: List<T>, total: Long, length: Int, current: Int) : this(data = PagingData<T>(list, total, length, current))
}
