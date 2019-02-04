package app.foodin.entity.common

enum class SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;


    companion object {

        val SIMPLE_OPERATION_SET = arrayOf(":", "!", ">", "<", "~")

        fun getSimpleOperation(input: Char): SearchOperation? {
            return when (input) {
                ':' -> EQUALITY
                '!' -> NEGATION
                '>' -> GREATER_THAN
                '<' -> LESS_THAN
                '~' -> LIKE
                else -> null
            }
        }
    }
}