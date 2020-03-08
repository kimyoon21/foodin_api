package app.foodin.common.utils

import app.foodin.common.exception.CommonException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import org.slf4j.LoggerFactory

class JwtUtils {

    companion object {
        private val logger = LoggerFactory.getLogger(JwtUtils::class.java)

        @Throws(Exception::class)
        fun decoded(jwt: String): String {
            try {
                val split = jwt.split(".")
                logger.info("Header: " + getJson(split[0]))
                logger.info("Body: " + getJson(split[1]))
                return getJson(split[1])
            } catch (e: UnsupportedEncodingException) {
                // Error
                throw CommonException("jwt 로그인 과정에서 결과 오류")
            }
        }

        @Throws(UnsupportedEncodingException::class)
        private fun getJson(strEncoded: String): String {
            val decodedBytes = Base64.getUrlDecoder().decode(strEncoded)
            return decodedBytes.toString(Charset.forName("UTF-8"))
        }
    }
}
