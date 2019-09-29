package app.foodin.common.exception

class NotExistsException(
    override val data: Any? = null,
    override val msgCode: String = EX_NOT_EXISTS_WHAT,
    vararg msgArgs: String?
) : CommonException(data, msgCode, *msgArgs) {
    constructor() : this(data = null, msgCode = EX_NOT_EXISTS)
    constructor(msgArg: String?) : this(null, EX_NOT_EXISTS_WHAT, msgArg)
}
