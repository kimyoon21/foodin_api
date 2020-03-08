package app.foodin.common.utils

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator

class CustomTaskDecorator : TaskDecorator {
    override fun decorate(task: Runnable): Runnable {

        val callerThreadContext = MDC.getCopyOfContextMap()
        return Runnable {
            MDC.setContextMap(callerThreadContext)
            task.run()
        }
    }
}
