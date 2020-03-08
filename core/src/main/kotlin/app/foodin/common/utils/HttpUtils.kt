package app.foodin.common.utils

import java.nio.charset.Charset
import org.apache.commons.codec.binary.Base64
import org.springframework.http.HttpHeaders

fun createBasicAuthHeaders(username: String, password: String): HttpHeaders {
    return object : HttpHeaders() {
        init {
            val auth = "$username:$password"
            val encodedAuth = Base64.encodeBase64(
                    auth.toByteArray(Charset.forName("US-ASCII")))
            val authHeader = "Basic " + String(encodedAuth)
            set("Authorization", authHeader)
        }
    }
}
