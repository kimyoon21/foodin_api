package kr.co.lendit.proxy

import org.aspectj.lang.reflect.MethodSignature
import java.lang.reflect.Method
import java.lang.reflect.Parameter

open class SignatureProcessor {
    val parameterPairs: List<Pair<String, Parameter>>
    private val signature: MethodSignature

    private val method: Method
    private val clazz: Class<Any>

    constructor(signature: MethodSignature) {
        this.signature = signature
        this.method = signature.method
        this.clazz = signature.declaringType

        val parameters = signature.method.parameters
        val parameterNames = signature.parameterNames
        parameterPairs = parameterNames.mapIndexed { idx, s -> s to parameters[idx] }
    }

    fun hasAnnotation(annClass: Class<out Annotation>): Boolean {
        return method.isAnnotationPresent(annClass) || clazz.isAnnotationPresent(annClass)
    }

    fun getAnnotation(annClass: Class<out Annotation>): Annotation? {
        return method.getAnnotation(annClass) ?: clazz.getAnnotation(annClass)
    }
}
