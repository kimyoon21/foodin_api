package app.foodin.domain

import app.foodin.common.enums.Status
import app.foodin.common.utils.getAuthenticatedUserInfo

abstract class StatusFilter : BaseFilter(){
    var status: Status? = null

    init {
        if (getAuthenticatedUserInfo().hasOnlyUserRole()) {
            status = Status.APPROVED
        }
    }
}
