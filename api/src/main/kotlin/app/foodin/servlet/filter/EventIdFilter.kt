package app.foodin.servlet.filter

import org.apache.commons.lang3.StringUtils
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.util.UUID
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

const val KEY_REQUEST_UID = "x-request-uid"
@Component
class EventIdFilter : Filter {
    override fun destroy() {}
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val path = (request as HttpServletRequest).requestURI
        var eventId = request.getHeader(KEY_REQUEST_UID)
        if (StringUtils.isEmpty(eventId)) {
            eventId = UUID.randomUUID().toString()
        }

        MDC.put("apiId", "$eventId : $path")
        MDC.put(KEY_REQUEST_UID, eventId)

        chain.doFilter(request, response)

        MDC.remove("apiId")
        MDC.remove(KEY_REQUEST_UID)
    }
    override fun init(filterConfig: FilterConfig) {}
}