package app.foodin.common.utils

import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

object DateHelper {
    private val log = LoggerFactory.getLogger(this.javaClass)

    // predefine 된 parser
    enum class Format(private val pattern: String) {
        Simple("yyyyMMdd"),
        Hyphen("yyyy-MM-dd");

        fun getPattern(): String = this.pattern

        fun findBy(format: String) = Format.values().find { it.pattern.equals(format) }
    }
    private val formatters: ConcurrentHashMap<String, DateTimeFormatter> by lazy {
        val map = ConcurrentHashMap<String, DateTimeFormatter>()
        Format.values().forEach { format ->
            map[format.getPattern()] = DateTimeFormatter.ofPattern(format.getPattern())
        }
        map
    }

    fun parse(dateStr: String, pattern: String = "yyyyMMdd"): LocalDate {
         val formatter = formatters[pattern] ?: try {
             DateTimeFormatter.ofPattern(pattern)
         } catch (e: IllegalArgumentException) {
             log.warn("잘못된 데이트 포맷: $pattern")
             formatters[Format.Simple.getPattern()]!!
         }
        return LocalDate.parse(dateStr, formatter)
    }
}