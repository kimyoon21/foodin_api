package app.foodin.servlet.filter

import app.foodin.common.utils.MDCUtils
import app.foodin.common.utils.MDCUtils.SESSION_ID
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Component
class SessionFilter : Filter {

    override fun init(filterConfig: FilterConfig) {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val sessionId = try {
            (request as HttpServletRequest).session.id
        } catch (e: Exception) {
            "-"
        }

        MDCUtils.set(SESSION_ID, sessionId)
        try {
            chain.doFilter(request, response)
        } finally {
            MDCUtils.remove(SESSION_ID)
        }
    }

    override fun destroy() {}
}