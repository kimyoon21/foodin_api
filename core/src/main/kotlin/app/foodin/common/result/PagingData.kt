package app.foodin.common.result

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page

class PagingData() {

    private val logger = LoggerFactory.getLogger(PagingData::class.java)

    var list: List<*> = ArrayList<Any>()
    var total: Long = 0
    var length: Int = 0
    var current: Int = 0

    constructor(page: Page<*>) : this() {
        list = page.toList()
        total = page.totalElements
        length = page.size
        current = page.number

    }

    constructor(list : List<*>, total : Long, length:Int = list.size, current:Int = 0) : this() {
        this.list = list
        this.total = total
        this.length = length
        this.current = current

    }


}
