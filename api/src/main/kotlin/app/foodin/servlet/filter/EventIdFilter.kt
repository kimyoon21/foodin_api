package app.foodin.servlet.filter

import app.foodin.common.utils.MDCUtils
import app.foodin.common.utils.MDCUtils.API_ID
import app.foodin.common.utils.MDCUtils.KEY_REQUEST_UID
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component

@Component
class EventIdFilter : Filter {
    override fun destroy() {}
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val path = (request as HttpServletRequest).requestURI
        var eventId = request.getHeader(KEY_REQUEST_UID)
        if (StringUtils.isEmpty(eventId)) {
            eventId = UUID.randomUUID().toString()
        }

        MDCUtils[API_ID] = "$eventId : $path"
        MDCUtils[KEY_REQUEST_UID] = eventId

        chain.doFilter(request, response)
    }
    override fun init(filterConfig: FilterConfig) {}
}
