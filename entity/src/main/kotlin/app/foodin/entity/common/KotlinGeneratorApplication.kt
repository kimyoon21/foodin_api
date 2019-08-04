package app.foodin.entity.common

import app.foodin.common.extension.toSnakeCase
import app.foodin.common.result.ResponseResult
import app.foodin.core.gateway.BaseGateway
import app.foodin.core.service.BaseService
import app.foodin.domain.BaseFilter
import app.foodin.domain.common.BaseDomain
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.io.File
import javax.persistence.Entity
import javax.persistence.Table

class KotlinGeneratorApplication

fun main(args: Array<String>) {

    var domainName: String = args[0]
    println("생성할 패키지의 Domain 이름 :$domainName")
    // lower camel case 로 도메인 명 받기
    if (domainName.isNullOrBlank() || domainName[0].isUpperCase()) {
        println("도메인명이 없거나 camel case 아님")
        System.exit(0)
    }

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
    makeCoreModuleFiles(domainName)
    makeEntityModuleFiles(domainName)

    println("완료")
}

fun makeApiModuleFiles(domainCamel: String) {
    val domainPascal = domainCamel.capitalize()
    val serviceClass = ClassName("app.foodin.core.service", "${domainPascal}Service")
    val filterClass = ClassName("app.foodin.domain.$domainCamel", "${domainPascal}Filter")
    val domainClass = ClassName("app.foodin.domain.$domainCamel", "$domainPascal")

    val file = FileSpec.builder("app.foodin.api.controller", "${domainPascal}Controller")
            .addType(TypeSpec.classBuilder("${domainPascal}Controller")
                    .addAnnotation(RestController::class)
                    .addAnnotation(AnnotationSpec.builder(RequestMapping::class)
                            .addMember("value = [%S]", "/$domainCamel")
                            .build())
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter("${domainCamel}Service", serviceClass)
                            .build())
                    .addProperty(PropertySpec.builder("${domainCamel}Service", serviceClass)
                            .initializer("${domainCamel}Service")
                            .mutable(false)
                            .build())
                    .addFunction(FunSpec.builder("getAll")
                            .addAnnotation(GetMapping::class)
                            .returns(ResponseResult::class)
                            .addParameter("pageable", Pageable::class)
                            .addParameter("filter", filterClass)
                            .addCode("""
        return ResponseResult(${domainCamel}Service.findAll(filter, pageable))
        |""".trimMargin())
                            .build())

                    /**
                     * @GetMapping(value = ["/{id}"])
                    fun getOne(@PathVariable id: Long): ResponseResult {
                     */
                    .addFunction(FunSpec.builder("getOne")
                            .addAnnotation(AnnotationSpec.builder(GetMapping::class)
                                    .addMember("value = [%S]", "/{id}")
                                    .build())
                            .returns(ResponseResult::class)
                            .addParameter(ParameterSpec.builder("id", Long::class)
                                    .addAnnotation(PathVariable::class)
                                    .build())
                            .addCode("""
        return ResponseResult(${domainCamel}Service.findById(id))
        |""".trimMargin())
                            .build())
                    .addFunction(FunSpec.builder("register")
                            .addAnnotation(AnnotationSpec.builder(PostMapping::class)
                                    .addMember("consumes = [%S]", "application/json")
                                    .build())
                            .returns(ResponseResult::class)
                            .addParameter(ParameterSpec.builder(domainCamel, domainClass)
                                    .addAnnotation(RequestBody::class)
                                    .build())
                            .addCode("""
        return ResponseResult(${domainCamel}Service.saveFrom($domainCamel))
        |""".trimMargin())
                            .build())

                    .build())

            .build()

    file.writeTo(File("api/src/main/kotlin/"))
}

fun makeCoreModuleFiles(domainCamel: String) {

    /****
     * @Service
    @Transactional
    class BannerService(
    override val gateway: BannerGateway
    ) : BaseService<Banner, BannerFilter>() {

    private val logger = LoggerFactory.getLogger(BannerService::class.java)

    fun findByBannerType(bannerType: BannerType, pageable: Pageable): Page<Banner> {
    return gateway.findByBannerType(bannerType, pageable)
    }
    }
     */
    val domainPascal = domainCamel.capitalize()
    val gatewayClass = ClassName("app.foodin.core.gateway", "${domainPascal}Gateway")
    val filterClass = ClassName("app.foodin.domain.$domainCamel", "${domainPascal}Filter")
    val domainClass = ClassName("app.foodin.domain.$domainCamel", "$domainPascal")
    val baseServiceClass = BaseService::class.asClassName()
    val serviceFile = FileSpec.builder("app.foodin.core.service", "${domainPascal}Service")
            .addType(TypeSpec.classBuilder("${domainPascal}Service")
                    .addAnnotation(Service::class)
                    .addAnnotation(Transactional::class)
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter("gateway", gatewayClass)
                            .build())
                    .addProperty(PropertySpec.builder("gateway", gatewayClass)
                            .initializer("gateway")
                            .mutable(false)
                            .addModifiers(KModifier.OVERRIDE)
                            .build())
                    .superclass(baseServiceClass.parameterizedBy(domainClass, filterClass))

                    .build())

            .build()

    serviceFile.writeTo(File("core/src/main/kotlin/"))

    /***
    interface BannerGateway : BaseGateway<Banner, BannerFilter> {
    interface BannerGateway : BaseGateway<Banner, BannerFilter> {
    } ***/
    val baseGatewayClass = BaseGateway::class.asClassName()
    val gatewayFile = FileSpec.builder("app.foodin.core.gateway", "${domainPascal}Gateway")
            .addType(TypeSpec.interfaceBuilder("${domainPascal}Gateway")
                    .addSuperinterface(baseGatewayClass.parameterizedBy(domainClass, filterClass))

                    .build())

            .build()

    gatewayFile.writeTo(File("core/src/main/kotlin/"))

    /****
     * data class Banner(
    override var id: Long = 0
    ) : BaseDomain(id) {
    }
     */
    val baseDomainClass = BaseDomain::class.asClassName()
    val domainFile = FileSpec.builder("app.foodin.domain.$domainCamel", "$domainPascal")
            .addType(TypeSpec.classBuilder("$domainPascal")
                    .addModifiers(KModifier.DATA)
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter(ParameterSpec.builder("id", Long::class).defaultValue("0L").build())
                            .build())
                    .addProperty(PropertySpec.builder("id", Long::class)
                            .initializer("id")
                            .mutable()
                            .addModifiers(KModifier.OVERRIDE)
                            .build())
                    .superclass(baseDomainClass).addSuperclassConstructorParameter("id")

                    .build())

            .build()

    domainFile.writeTo(File("core/src/main/kotlin/"))

    /***
     * data class BannerFilter(
    ) : BaseFilter()
     */
    val baseFilterClass = BaseFilter::class.asClassName()
    val filterFile = FileSpec.builder("app.foodin.domain.$domainCamel", "${domainPascal}Filter")
            .addType(TypeSpec.classBuilder("${domainPascal}Filter")
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter(ParameterSpec.builder("field1", String::class.asTypeName().copy(nullable = true)).defaultValue("null").build())
                            .build())
                    .addProperty(PropertySpec.builder("field1", String::class.asTypeName().copy(nullable = true))
                            .initializer("field1")
                            .mutable(false)
                            .build())
                    .addModifiers(KModifier.DATA)
                    .superclass(baseFilterClass)
                    .build())
            .build()

    filterFile.writeTo(File("core/src/main/kotlin/"))
}

fun makeEntityModuleFiles(domainCamel: String) {

    val domainPascal = domainCamel.capitalize()
    val domainSnake = domainPascal.toSnakeCase()
    val gatewayClass = ClassName("app.foodin.core.gateway", "${domainPascal}Gateway")
    val filterClass = ClassName("app.foodin.domain.$domainCamel", "${domainPascal}Filter")
    val domainClass = ClassName("app.foodin.domain.$domainCamel", "$domainPascal")
    val entityClass = ClassName("app.foodin.entity.$domainCamel", "${domainPascal}Entity")
    val repositoryClass = ClassName("app.foodin.entity.$domainCamel", "${domainPascal}Repository")

    /****
     * @Entity
    @Table(name = "review")
    data class ReviewEntity(
    val foodId: Long
    ) : BaseEntity<Review>(){
    override fun toDomain(): Review {
    //TODO
    }
    }
     */
    val baseEntityClass = BaseEntity::class.asClassName()
    val entityFile = FileSpec.builder("app.foodin.entity.$domainCamel", "${domainPascal}Entity")
            .addType(TypeSpec.classBuilder("${domainPascal}Entity")
                    .addAnnotation(Entity::class)
                    .addAnnotation(AnnotationSpec.builder(Table::class)
                            .addMember("name = %S", domainSnake)
                            .build())
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter(ParameterSpec.builder("field1", String::class).build())
                            .build())
                    .addProperty(PropertySpec.builder("field1", String::class)
                            .initializer("field1")
                            .mutable(false)
                            .build())
                    .superclass(baseEntityClass.parameterizedBy(domainClass))
                    .addModifiers(KModifier.DATA)
                    .addFunction(FunSpec.builder("toDomain")
                            .returns(domainClass)
                            .addModifiers(KModifier.OVERRIDE)
                            .addCode("""
                                |return $domainPascal(id).also {
            setDomainBaseFieldsFromEntity(it)
            TODO("not implemented")
        }
        """.trimMargin())
                            .build())

                    .build())

            .build()

    entityFile.writeTo(File("entity/src/main/kotlin/"))

    /***
     * class ReviewFilterQuery(
    val filter: ReviewFilter
    ) : BaseFilterQuery<Review, ReviewEntity> {
    override fun toSpecification(): Specification<ReviewEntity> {

    }
     */
    val baseFilterQueryClass = BaseFilterQuery::class.asClassName()
    val specClass = Specification::class.asClassName()
    val filterQueryFile = FileSpec.builder("app.foodin.entity.$domainCamel", "${domainPascal}FilterQuery")
            .addType(TypeSpec.classBuilder("${domainPascal}FilterQuery")
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter(ParameterSpec.builder("filter", filterClass)
                                    .build())
                            .build())
                    .addProperty(PropertySpec.builder("filter", filterClass)
                            .initializer("filter")
                            .mutable(false)
                            .build())
                    .addSuperinterface(baseFilterQueryClass.parameterizedBy(domainClass, entityClass))
                    .addFunction(FunSpec.builder("toSpecification")
                            .returns(specClass.parameterizedBy(entityClass))
                            .addModifiers(KModifier.OVERRIDE)
                            .addCode("""
        TODO("not implemented")
        |""".trimMargin())
                            .build())
                    .build())
            .build()

    filterQueryFile.writeTo(File("entity/src/main/kotlin/"))

    /***
     *@Repository
    interface ReviewRepository : BaseRepositoryInterface<ReviewEntity> {

    }

    @Component
    class JpaReviewRepository(private val repository: ReviewRepository) :
    BaseRepository<Review, ReviewEntity, ReviewFilter>(repository), ReviewGateway {
    override fun saveFrom(review: Review): Review {
     */
    val baseRepositoryInterface = BaseRepositoryInterface::class.asClassName()
    val baseRepositoryClass = BaseRepository::class.asClassName()
    val pageClass = Page::class.asClassName()
    val filterQueryClass = ClassName("app.foodin.entity.$domainCamel", "${domainPascal}FilterQuery")

    val repositoryFile = FileSpec.builder("app.foodin.entity.$domainCamel", "${domainPascal}Repository")
            .addType(TypeSpec.interfaceBuilder("${domainPascal}Repository")
                    .addSuperinterface(baseRepositoryInterface.parameterizedBy(entityClass))
                    .addAnnotation(Repository::class)
                    .build())

            .addType(TypeSpec.classBuilder("Jpa${domainPascal}Repository")
                    .addAnnotation(Component::class)
                    .primaryConstructor(FunSpec.constructorBuilder()
                            .addParameter(ParameterSpec.builder("repository", repositoryClass)
                                    .build())
                            .build())
                    .addProperty(PropertySpec.builder("repository", repositoryClass)
                            .initializer("repository")
                            .addModifiers(KModifier.PRIVATE)
                            .mutable(false)
                            .build())
                    .superclass(baseRepositoryClass.parameterizedBy(domainClass, entityClass, filterClass))
                    .addSuperclassConstructorParameter("repository")
                    .addSuperinterface(gatewayClass)
                    .addFunction(FunSpec.builder("findAllByFilter")
                            .returns(pageClass.parameterizedBy(domainClass))
                            .addParameter("pageable", Pageable::class)
                            .addParameter("filter", filterClass)
                            .addModifiers(KModifier.OVERRIDE)
                            .addStatement("""
        return findAll(%T(filter), pageable)
        |""".trimMargin(),filterQueryClass)
                    .build())


                    .addFunction(FunSpec.builder("saveFrom")
                            .returns(domainClass)
                            .addParameter("$domainCamel", domainClass)
                            .addModifiers(KModifier.OVERRIDE)
                            .addStatement("""
        return repository.saveAndFlush(%T(t)).toDomain()
        |""".trimMargin(),entityClass)
                            .build())
                    .build())

            .build()

    repositoryFile.writeTo(File("entity/src/main/kotlin/"))
}
