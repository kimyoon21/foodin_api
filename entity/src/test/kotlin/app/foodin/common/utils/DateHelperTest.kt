package app.foodin.common.utils

import org.junit.Assert.assertEquals
import org.junit.Test

import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateHelperTest {

    @Test(expected = IllegalArgumentException::class)
    fun `find`() {
        DateHelper.Format.valueOf("yyyy-mm-dd")
    }

    @Test
    fun `parse`() {
        val date = LocalDate.of(2019, 1, 1)
        val newDate = DateHelper.parse("20190101")
        assertEquals(date.toString(), newDate.toString())
    }

    @Test(expected = DateTimeParseException::class)
    fun `new parse 실패`() {
        val date = LocalDate.of(2019, 1, 1)
        val newDate = DateHelper.parse("2019-01-01", "yyyyMMdd")
        assertEquals(date.toString(), newDate.toString())
    }

    @Test
    fun `new parse`() {
        val date = LocalDate.of(2019, 1, 1)
        val newDate = DateHelper.parse("2019-01-01", "yyyy-MM-dd")
        assertEquals(date.toString(), newDate.toString())
    }
}