package app.foodin.servlet.filter

import app.foodin.servlet.MultiReadableHttpServletRequest
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(value = 1)
class MultiReadableHttpServletRequestFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val multiReadRequest = MultiReadableHttpServletRequest(req as HttpServletRequest)

        chain.doFilter(multiReadRequest, res)
    }

    override fun init(filterConfig: FilterConfig?) {}

    override fun destroy() {}
}
