package app.foodin.core.config

import app.foodin.common.exception.CustomAsyncExceptionHandler
import app.foodin.common.utils.CustomTaskDecorator
import java.util.concurrent.Executor
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableAsync
class SpringAsyncConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().also {
            it.corePoolSize = 5
            it.maxPoolSize = 30
            it.setQueueCapacity(10)
            it.setThreadNamePrefix("Async-Executor-")
            it.setTaskDecorator(CustomTaskDecorator())
            it.initialize()
        }
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return CustomAsyncExceptionHandler()
    }
}
