package app.foodin.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
    companion object {
        // 아래 어노테이션은 이 코드를 java 에서사용하기 위한 거니까 우린 없어도 됨 @kimyoon
//        @JvmStatic
        private lateinit var context: ApplicationContext

        private val mapper: ObjectMapper by lazy {
            val mapper = ObjectMapper()
            val module = KotlinModule()
            mapper.registerModule(module)
            mapper
        }

        @Bean
        fun getObjectMapper() = mapper

        fun <T> getBean(beanClass: Class<T>): T = context.getBean(beanClass)
    }
}
