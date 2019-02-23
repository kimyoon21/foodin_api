package app.foodin.common.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.InputStream

object JsonUtils {
    private val logger = LoggerFactory.getLogger(JsonUtils::class.java)
    private val mapper: ObjectMapper by lazy {
        val mapper = ObjectMapper()

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true)
        mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true)
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
        val module = KotlinModule()
        mapper.registerModule(module)
    }

    fun toJson(any: Any): String {
        try {
            return mapper.writer().writeValueAsString(any)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(JsonProcessingException::class)
    fun toJsonWithException(any: Any): String {
        return mapper.writer().writeValueAsString(any)
    }

    fun <T> fromJson(jsonStr: String, cls: Class<T>): T {
        try {
            // use reader to increase multi thread performance
            return mapper.readerFor(cls).readValue(jsonStr)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun <T> fromJsonWithException(jsonStr: String, cls: Class<T>): T {
        // use reader to increase multi thread performance
        return mapper.readerFor(cls).readValue(jsonStr)
    }

    fun <T> fromJson(stream: InputStream, cls: Class<T>): T {
        try {
            return mapper.readerFor(cls).readValue(stream)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun <T> fromJson(jsonStr: String, typeReference: TypeReference<T>): T {
        try {
            return mapper.readerFor(typeReference).readValue(jsonStr)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(Exception::class)
    fun fromJson(json: String): JsonNode {
        try {
            return mapper.readTree(json)
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }
    }

    fun <T : Collection<*>> fromJson(jsonStr: String, collectionType: CollectionType): T {
        try {
            return mapper.readerFor(collectionType).readValue(jsonStr)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun toPrettyJson(json: String?): String {
        if (json != null) {
            val jsonObject = app.foodin.common.utils.JsonUtils.fromJson(json, Any::class.java)
            try {
                return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(jsonObject)
            } catch (e: JsonProcessingException) {
                logger.error(" error in JsonUtils: pretty json",e)
            }
        }
        return ""
    }
}

