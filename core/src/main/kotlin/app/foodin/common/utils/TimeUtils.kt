package app.foodin.common.utils

import app.foodin.common.exception.CommonException
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/****
 * 그주의 시작 월요일
 * @param t
 * @return
 */
////fun getMondayOfTheWeek(t: Timestamp): Timestamp {
////    val now = LocalDate.of()
////    val monday = now.withDayOfWeek(DateTimeConstants.MONDAY)
////    return Timestamp(monday.toDateTimeAtStartOfDay().getMillis())
////
//}

/****
 * 그날의 종료시간 추출
 * @param t
 * @return
 */
//fun getEndOfDay(t: Timestamp): Timestamp {
//    var dateTime = LocalDateTime(t)
//    dateTime = dateTime.millisOfDay().withMaximumValue()
//    return Timestamp(dateTime.toDateTime().getMillis())
//}
//
//fun getNextBusinessDay(): Timestamp {
//    var next = LocalDateTime.now()
//    next = next.plusDays(1)
//    while (next.getDayOfWeek() === DateTimeConstants.SATURDAY || next.getDayOfWeek() === DateTimeConstants.SUNDAY) {
//        next = next.plusDays(1)
//    }
//    return Timestamp(next.millisOfDay().withMinimumValue().toDateTime().getMillis())
//}

/**
 * 매번 반복되고 매번 import 가 필요한 DB 저장용 Timestamp 현재 시각 가져오기
 *
 * @return 현재시각 (Timestamp형)
 */
fun now(): Timestamp {
    return Timestamp(Date().time)
}


/**
 * @param timeStr
 * @return
 * @Method_Name : strToTimestamp
 * @작성일 : 2016. 5. 29.
 * @작성자 : kimyoon
 * @설명 : 지정된 포맷을 이용해서 스트링 값을 timestamp 로 변환해준다. format 없을기 기본값은 yyyyMMddHHmmss (Constants.DEFAULT_DATE_FORMAT)
 * @변경이력 :
 */
fun strToTimestamp(timeStr: String): Timestamp {
    return strToTimestamp(timeStr, DEFAULT_DATE_FORMAT)
}

fun strToTimestamp(timeStr: String, format: String): Timestamp {
    val sdf = SimpleDateFormat(format)
    var result: Timestamp?
    try {
        val date = sdf.parse(timeStr)
        result = Timestamp(date.time)
    } catch (e: ParseException) {
        e.printStackTrace()
        throw CommonException(e.message!!)
    }

    return result
}

/***
 * Timestamp 를 날짜형태로 변경
 * @param ts
 * @param format
 * @return
 */
fun timestampToStr(ts: Timestamp, format: String): String {
    val sdf = SimpleDateFormat(format)
    val date = Date(ts.time)
    return sdf.format(date)
}


/***
 * 몇달전 timestamp 값 얻기. 음수를 넣으면 몇달 후 값
 * @param n
 * @return
 */
fun getNMonthAgo(n: Int): Timestamp {
    return Timestamp(System.currentTimeMillis() - A_MONTH_TO_MILLISEC * n)
}

fun getNMonthAgo(n: Int, ts: Timestamp): Timestamp {
    return Timestamp(ts.time - A_MONTH_TO_MILLISEC * n)
}

/***
 * 며칠전
 * @param n
 * @return
 */
fun getNDayAgo(n: Int): Timestamp {
    return Timestamp(System.currentTimeMillis() - A_DAY_TO_MILLISEC * n)
}

fun getNHoursAgo(n: Int): Timestamp {
    return Timestamp(System.currentTimeMillis() - n * 1000 * 60 * 60)
}

fun getNSecAgo(n: Int): Timestamp {
    return Timestamp(System.currentTimeMillis() - n * 1000)
}
