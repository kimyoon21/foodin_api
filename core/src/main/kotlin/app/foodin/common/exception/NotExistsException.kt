package app.foodin.common.exception

class NotExistsException(
    override val data: Any? = null,
    override val msgCode: String = EX_NOT_EXISTS_WHAT,
    override val msgArgs: Array<out String>? = null
) : CommonException(data, msgCode, msgArgs) {
    constructor() : this(null, EX_NOT_EXISTS, null)
    constructor(vararg msgArgs: String) : this(null, EX_NOT_EXISTS_WHAT, msgArgs)
}
