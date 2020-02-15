package app.foodin.aop.requestLog

import app.foodin.common.utils.JsonUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.springframework.stereotype.Component

@Component
class RequestLogObject {
    var clientIp: String? = null
    var method: String? = null
    var status: Int? = null
    var path: String? = null
    var eventId: String? = null
    var requestTime: LocalDateTime? = null
    var execTime: Long? = null
    var response: Any? = null
    var params = HashMap<String, Any>()

    @JsonIgnore
    var startTime: LocalDateTime? = null

    fun setTime(time: LocalDateTime) {
        this.requestTime = time
    }

    fun toJson(): String {
        return JsonUtils.toJson(this)
    }
}

/**
 * ThreadLocal 은 리퀘스트마다 별도의 context 를 가지므로
 */
object RequestLog {
    private val STATUS = ThreadLocal<RequestLogObject>()

    private fun instance(): RequestLogObject {
        return STATUS.get() ?: run {
            var sm = RequestLogObject()
            sm.setTime(LocalDateTime.now())
            STATUS.set(sm)
            sm
        }
    }

    fun start(method: String, path: String, eventId: String, clientIp: String) {
        val sm = instance()
        sm.method = method
        sm.path = path
        sm.eventId = eventId
        sm.clientIp = clientIp
    }

    fun json(): String {
        return instance().toJson()
    }

    fun params(): MutableMap<String, Any> {
        return instance().params
    }

    fun calExecuteTime(): Long {
        return instance().requestTime?.until(LocalDateTime.now(), ChronoUnit.MILLIS) ?: -1
    }

    /**
     * status code,  body
     */
    fun finish(status: Int) {
        val sm = instance()
        sm.execTime = calExecuteTime()
        sm.status = status
    }

    var response: Any? = null
        set(value) {
            try {
                // cause == this 무한 중첩 self 참조이므로 막는다 TODO 이거 어찌 처리하지
                if (value is Exception && value.cause != null) {
                    if (value == value.cause) {
                        value.initCause(null)
                    } else if (value.cause == value.cause?.cause) {
                        value.cause?.initCause(null)
                    }
                }
            } catch (e: Exception) {
                field = (value as Exception).message
            }
            field = value
        }

    fun close() {
        STATUS.remove()
    }
}
