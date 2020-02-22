package app.foodin.common.result

import org.springframework.data.domain.Page

open class ResponseResult(
    open var message: String? = "ok",
    open var data: Any? = null
) {

    open var succeeded: Boolean = true

    constructor(successData: Any?) : this(data = successData){
        if (data == false) {
            message = "not affected"
            succeeded = false
        }
    }

    constructor(page: Page<*>) : this(PagingData(page))

    constructor(list: List<*>, total: Long, length: Int, current: Int) : this(PagingData(list, total, length, current))
}
