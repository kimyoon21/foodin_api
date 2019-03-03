package app.foodin.common.utils

const val USERNAME_SEPERATOR = "::"

const val DEFAULT_DATE_FORMAT = "yyyyMMdd"

/**
 * 1분을 milli sec
 */
const val A_MINUTE_TO_MILLISEC = 60.toLong() * 1000
        
/**
 * 1시간을 milli sec
 */
const val AN_HOUR_TO_MILLISEC = 60.toLong() * 60 * 1000
        
/**
 * 24시간을 milli sec
 */
const val A_DAY_TO_MILLISEC = 24.toLong() * 60 * 60 * 1000
/**
 * 1주일을 milli sec
 */
const val A_WEEK_TO_MILLISEC = A_DAY_TO_MILLISEC * 7
/**
 * 1ekf을 milli sec
 */
const val A_MONTH_TO_MILLISEC = A_DAY_TO_MILLISEC * 30