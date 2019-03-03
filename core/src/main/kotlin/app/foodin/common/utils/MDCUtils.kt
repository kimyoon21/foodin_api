package app.foodin.common.utils

import app.foodin.common.extension.hasValue
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import javax.servlet.ServletRequest

object MDCUtils {

    private val mdc = MDC.getMDCAdapter()
    val HEADER_MAP_MDC = "HEADER_MAP_MDC"
    val PARAMETER_MAP_MDC = "PARAMETER_MAP_MDC"
    val USER_INFO_MDC = "USER_INFO_MDC"
    val AUTH_TYPE = "AUTH_TYPE"
    val AGENT_TYPE = "AGENT_TYPE"
    val REQUEST_URI_MDC = "REQUEST_URI_MDC"
    val REQUEST_METHOD_MDC = "REQUEST_METHOD_MDC"
    val SERVER_INFO_MDC = "SERVER_INFO_MDC"
    val REQUEST_IP = "REQUEST_IP_MDC"
    val APP_VERSION_CODE = "APP_VERSION_CODE"
    val DEVICE_TYPE = "DEVICE_TYPE"
    val PUSH_TOKEN = "PUSH_TOKEN"
    val AUTH_SKIP_CASE = "AUTH_SKIP_CASE"
    val USERNAME = "USERNAME"
    val USER_ID = "USER_ID"
    val USER_LOGIN_ID = "USER_LOGIN_ID"
    val USER_REAL_NAME = "USER_REAL_NAME"
    val USER_NICK_NAME = "USER_NICK_NAME"
    val KEY_REQUEST_UID = "x-request-uid"
    val API_ID = "API_ID"
    val SESSION_ID = "SESSION_ID"

    private val logger = LoggerFactory.getLogger(MDCUtils::class.java)

    operator fun set(key: String, value: String) {
        mdc.put(key, value)
    }

    fun setJsonValue(key: String, value: Any?) {
        try {
            if (value != null) {
                val json = JsonUtils.toJson(value)
                mdc.put(key, json)
            }
        } catch (e: Exception) {
            logger.error("error in toJson or put", e)
        }
    }

    fun has(key: String): Boolean {
        return mdc.get(key).hasValue()
    }

    operator fun get(key: String): String {
        return mdc.get(key)
    }

    fun getOrEmpty(key: String): String {
        return mdc.get(key) ?: ""
    }

    fun getAllMap(): MutableMap<String, String>? {
        return mdc.copyOfContextMap
    }

    fun clear() {
        MDC.clear()
    }

    fun setErrorAttribute(errorAttribute: Map<String, Any>) {
        if (errorAttribute.containsKey("path")) {
            set(REQUEST_URI_MDC, errorAttribute["path"] as String)
        }
    }

    fun setMDCRequestFieldsInFilter(request: ServletRequest) {

        val requestWrapper = RequestWrapper.of(request)

        // Set Http Header
        setJsonValue(HEADER_MAP_MDC, requestWrapper.headerMap)

//        // Set Agent Detail
//        setJsonValue(AGENT_DETAIL_MDC, AgentUtils.getAgentDetail(request))
        // set Agent type
        set(AGENT_TYPE, requestWrapper.agentType)

        set(DEVICE_TYPE, requestWrapper.deviceType)
        set(APP_VERSION_CODE, requestWrapper.appVersionCode)

        // Set Http Body
        setJsonValue(PARAMETER_MAP_MDC, requestWrapper.parameterMapOrList)

        // Set Server Info
        setJsonValue(SERVER_INFO_MDC, request.serverName + ":" + request.serverPort)

        // Set Http Request URI
        set(REQUEST_URI_MDC, requestWrapper.requestUri)
        // Set Http Request METHOD
        set(REQUEST_METHOD_MDC, requestWrapper.requestMethod)
        // Set Http Request src IP
        set(REQUEST_IP, requestWrapper.requestSrcIp)
    }

    fun remove(key: String) {
        MDC.remove(key)
    }
}
