package app.foodin.common.result

import org.springframework.data.domain.Page

open class ResponseResult(
    var message: String? = null,
    var data: Any? = null
) {

    constructor(successData: Any?) : this(data = successData)

    constructor(page: Page<*>) : this(PagingData(page))

}
