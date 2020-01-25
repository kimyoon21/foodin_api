package app.foodin.servlet.filter

import app.foodin.common.utils.MDCUtils
import java.io.IOException
import javax.servlet.*
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(value = 2)
class MDCFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        MDCUtils.setMDCRequestFieldsInFilter(request)

        try {
            chain.doFilter(request, response)
        } finally {
            MDCUtils.clear()
        }
    }

    override fun init(filterConfig: FilterConfig?) {}

    override fun destroy() {}
}
