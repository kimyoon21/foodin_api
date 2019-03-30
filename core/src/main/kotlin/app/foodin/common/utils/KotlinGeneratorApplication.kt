package app.foodin.common.utils

import app.foodin.common.result.ResponseResult
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

class KotlinGeneratorApplication

fun main(args: Array<String>) {
    var domainName: String = args[0]
    // snake case 로 도메인 명 받기
    if (domainName.isNullOrBlank() || domainName[0].isUpperCase()) {
        System.exit(0)
    }
    println("만들어진 Domain 이름 :$domainName")


    /****
     * 생성할 파일(New) 종류
     * api module
     * - api.controller.NewController
     *
     * core module
     * - gateway.NewGateway
     * - service.NewService
     * - domain.new.New
     * - domain.new.NewFilter
     *
     * entity module
     * - new.NewEntity
     * - new.NewFilterQuery
     * - new.NewRepository
     *
     */

    makeApiModuleFiles(domainName)


}

fun makeApiModuleFiles(domainSnake: String) {
    val domainCapital = domainSnake.capitalize()
    val file = FileSpec.builder("app.foodin.api.controller", "${domainCapital}Controller")
            .addType(TypeSpec.classBuilder("${domainCapital}Controller")
                    .addAnnotation(RestController::class)
                    .addAnnotation(AnnotationSpec.builder(RequestMapping::class)
                            .addMember("value = %S", "/$domainSnake")
                            .build())
                    .addFunction(FunSpec.builder("getAll")
                            .addAnnotation(GetMapping::class)
                            .returns(ResponseResult::class)
                            .addParameter("pageable", Pageable::class)
                            .addParameter("filter", "${domainCapital}Filter"::class)
                            .addCode("""
        return ResponseResult(${domainCapital}Service.findAll(filter, pageable))
        |""".trimMargin())
                            .build())

                    .build())


            .build()

    file.writeTo(File("api/src/main/kotlin/"))
}