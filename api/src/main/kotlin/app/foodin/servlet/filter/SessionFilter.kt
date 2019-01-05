package app.foodin.servlet.filter

import org.slf4j.MDC
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class SessionFilter : Filter {

    override fun init(filterConfig: FilterConfig) {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val sessionId = try {
            (request as HttpServletRequest).session.id
        } catch (e: Exception) {
            "-"
        }

        MDC.put("sessionId", sessionId)
        try {
            chain.doFilter(request, response)
        } finally {
            MDC.remove("sessionId")
        }
    }

    override fun destroy() {}
}