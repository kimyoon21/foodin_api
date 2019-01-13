package app.foodin.core.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Loggable(
    val param: Boolean = true,
    val result: Boolean = false
)

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class IgnoreLoggable
