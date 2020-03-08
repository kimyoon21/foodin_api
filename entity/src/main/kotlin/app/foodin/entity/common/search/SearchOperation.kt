package app.foodin.entity.common.search

enum class SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN,
    LIKE, STARTS_WITH, ENDS_WITH, CONTAINS,
    NULL_CHECK, IS_NULL, IS_NOT_NULL;

    companion object {

        val SIMPLE_OPERATION_SET = arrayOf(":", "!", ">", "<", "~", "@")

        fun getSimpleOperation(input: Char): SearchOperation? {
            return when (input) {
                ':' -> EQUALITY
                '!' -> NEGATION
                '>' -> GREATER_THAN
                '<' -> LESS_THAN
                '~' -> LIKE
                '@' -> NULL_CHECK
                else -> null
            }
        }
    }
}
