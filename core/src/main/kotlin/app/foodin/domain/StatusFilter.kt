package app.foodin.domain

import app.foodin.common.enums.Status

abstract class StatusFilter : BaseFilter(){
    var status: Status? = null
}
