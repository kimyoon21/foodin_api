package app.foodin.common.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest


class RequestWrapper private constructor(val request: HttpServletRequest) {

    val headerMap: Map<String, String>
        get() {
            val convertedHeaderMap = HashMap<String, String>()

            val headerMap = request.headerNames

            while (headerMap.hasMoreElements()) {
                val name = headerMap.nextElement()
                val value = request.getHeader(name)

                convertedHeaderMap[name] = value
            }
            return convertedHeaderMap
        }

    /***
     * @Method_Name : parameterMapOrList
     * @작성일 : 2016. 3. 16.
     * @작성자 : kimyoon
     * @설명 : parameter map 생성.(파라미터가 어레이라면 list 생성). 만약 비어있는 경우에는 인풋스트림을 읽는다. (그래도 없는 경우는 data none exist case)
     * @변경이력 :
     * @return
     */
    //inputStreaming => String : logging
    // Param 이 없는 application/json 형태의 인풋스트림만 변경.
    // 라이브러리에 따라 프로토콜을 제대로 지키지 않아 파일을 업로드 했는데, 파일로 인식하지 못하는 경우가 있다.
    // 가령, Content-Disposition 에 filename 을 넣지 않고, Content-Type 을 multipart/form-data 로 넣는 경우에는 파일 바이너리를 value 로 담게 된다.
    // 바이너리가 들어오는 걸 막을 수 없다고 치면, 길이를 제한하여 너무 많은 데이터를 DB에 저장하려고 하지 않게 막자.
    // MySQL text(65,535) 크기가 넘지 않도록, 파라미터맵의 다른 키도 동일한 데이터를 담을 수 있도록 나눠준다
    // 키의 데이터와 JSON 형식으로 변환하는 과정에서 생성되는 브라켓 등의 데이터를 20 바이트로 산정
    // JSON 으로 표현되지 않는 데이터는 \u0000 로 치환되므로 6으로 추가로 나눔
    // it might be JOSNArray case. retry for array
    //do Nothing
    val parameterMapOrList: Any
        get() {
            val parameterMap = request.parameterMap
            var convertedParameterMap: MutableMap<String, Any> = HashMap()
            logger.info("---> request.getContentType : " + request.contentType)
            var logStr = ""
            var inputString = ""
            try {

                if ((parameterMap == null || parameterMap.isEmpty()) && request.inputStream != null) {
                    if (null != request.contentType && request.contentType.startsWith("application/json")) {
                        inputString = IOUtils.toString(request.inputStream, request.characterEncoding)
                        logStr = inputString
                        if (inputString.trim().isNullOrEmpty()) {
                            return convertedParameterMap
                        } else if (inputString.length > 200) {
                            logStr = inputString.substring(0, 200)
                        }
                        logger.info("HTTP request inputStream read to String : $logStr ... ")
                        convertedParameterMap = JsonUtils.fromJson(
                                inputString,
                                object : TypeReference<MutableMap<String, Any>>() {}
                        )
                    }
                } else if (parameterMap != null) {
                    val maxLength = (65535 / parameterMap.size - 20) / 6

                    for (key in parameterMap.keys) {
                        val values = parameterMap[key]
                        val valueString = StringJoiner(",")

                        for (value in values!!) {
                            valueString.add(value)
                        }

                        var value = valueString.toString()
                        if (value.length > maxLength && hasOtherSymbols(value)) {
                            value = "표시안함"
                        }
                        convertedParameterMap[key] = value
                    }
                }
            } catch (ee: RuntimeException) {
                if (!inputString.isEmpty()) {
                    try {
                        return JsonUtils.fromJson(inputString, object : TypeReference<List<Map<String, Any>>>() {})
                    } catch (e: Exception) {
                        logger.info("request inputStream read to Array Error => skip : $logStr", e)
                    }

                }
            } catch (ee: JsonMappingException) {
                if (!inputString.isEmpty()) {
                    try {
                        return JsonUtils.fromJson(inputString, object : TypeReference<List<Map<String, Any>>>() {})
                    } catch (e: Exception) {
                        logger.info("request inputStream read to Array Error => skip : $logStr", e)
                    }

                }
            } catch (e: Exception) {
                logger.info("request inputStream read to Map Error => skip : $logStr", e)
            }

            return convertedParameterMap
        }

    val requestUri: String
        get() = request.requestURI

    val requestMethod: String
        get() = request.method

    // ELB 뒤에서 기존 아이피 획득
    val requestSrcIp: String
        get() {
            fun isInvalidIp(clientIp: String?): Boolean {
                return clientIp.isNullOrEmpty() || StringUtils.equalsIgnoreCase(clientIp, "unknown")
            }

            var clientIp = request.getHeader("X-FORWARDED-FOR")
            if (isInvalidIp(clientIp)) {
                clientIp = request.getHeader("X-Real-IP")
            }
            if (isInvalidIp(clientIp)) {
                clientIp = request.remoteAddr
            }
            return clientIp.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].trim()
        }

    // app version code 획득.
    val appVersionCode: String
        get() = request.getHeader("appversioncode") ?: ""

    // app device type 획득.
    val deviceType: String
        get() = request.getHeader("devicetype") ?: ""

    // 머큐리 및 앱 구분위한 agent type
    // 161010 agent 헤더에 머큐리 값이 있으면 머큐리웹, 아니면 앱
    // Mercury or Admin
    val agentType: String
        get() = if (request.getHeader("agent").isNullOrEmpty()) {
            "APP"
        } else {
            request.getHeader("agent")
        }

    /**
     * JSON 으로 표현이 불가능하여 \u0000 와 같이 표현될만한 데이터가 있는지 체크
     *
     * @param input
     * @return
     */
    private fun hasOtherSymbols(input: String): Boolean {
        val charArray = input.toCharArray()
        for (c in charArray) {
            if (Character.getType(c) == Character.OTHER_SYMBOL.toInt()) {
                return true
            }
        }
        return false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RequestWrapper::class.java)

        fun of(request: HttpServletRequest): RequestWrapper {
            return RequestWrapper(request)
        }

        fun of(request: ServletRequest): RequestWrapper {
            return of(request as HttpServletRequest)
        }
    }
}
