package de.kotlinBerlin.kMapper.internal

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.extensions.path
import de.kotlinBerlin.kMapper.extensions.readConverted
import kotlin.reflect.*
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.isSupertypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

internal class BasicDefaultMappingConfig<S, T>(private val sourceType: KType, private val targetType: KType) :
    DefaultMappingConfig<S, T> {

    private val excludedSourceProperties = mutableListOf<KProperty1<S, *>>()
    private val excludedTargetProperties = mutableListOf<KMutableProperty1<T, *>>()

    private lateinit var sourceClass: KClass<*>
    private lateinit var targetClass: KClass<*>

    private lateinit var paramMatcher: (KParameter) -> KProperty1<S, *>
    private var detectPrimaryConstructor = false

    //Lazy to be executed only after everything is configured!
    private val targetProperties by lazy {
        targetClass.memberProperties.filterIsInstance<KMutableProperty1<T, *>>().filterNot(excludedTargetProperties::contains)
    }

    private var propertyMatcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>? = { sourceProp -> targetProperties.find { it.name == sourceProp.name } }

    @ExperimentalStdlibApi
    private var mappingResolver: (KType, KType) -> Mapping<*, *>? = ::findMatchingMapping

    override fun KProperty1<S, *>.exclude() {
        excludedSourceProperties.add(this)
    }

    override fun KMutableProperty1<T, *>.exclude() {
        excludedTargetProperties.add(this)
    }

    override fun withPropertyMatcher(matcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>?) {
        propertyMatcher = matcher
    }

    @ExperimentalStdlibApi
    override fun withMappingResolver(resolver: (KType, KType) -> Mapping<*, *>?) {
        mappingResolver = resolver
    }

    @Suppress("UNCHECKED_CAST")
    override fun detectPrimaryConstructor() {
        detectPrimaryConstructor { parameter ->
            sourceClass.memberProperties.find { it.name == parameter.name } as? KProperty1<S, *>
                ?: throw java.lang.IllegalArgumentException("Can not find matching property for parameter: ${parameter.name}")
        }
    }

    override fun detectPrimaryConstructor(matcher: (KParameter) -> KProperty1<S, *>) {
        paramMatcher = matcher
        detectPrimaryConstructor = true
    }

    @ExperimentalStdlibApi
    @Suppress("UNCHECKED_CAST")
    fun buildMappings(): Map<ReadPath<S, Any?>, WritePath<T, Nothing>> {
        sourceClass = sourceType.classifier as? KClass<*> ?: throw IllegalArgumentException("Unable to perform default mapping!")
        targetClass = targetType.classifier as? KClass<*> ?: throw IllegalArgumentException("Unable to perform default mapping!")

        return sourceClass.memberProperties
            .map { it as KProperty1<S, *> }
            .filterNot(excludedSourceProperties::contains)
            .mapNotNull { sourceProp ->
                val tempSourceType = sourceProp.returnType
                val tempTargetProp = propertyMatcher(sourceProp)

                tempTargetProp?.let { targetProp ->
                    val tempTargetType = targetProp.returnType

                    if (tempSourceType == tempTargetType) {
                        sourceProp.path to targetProp.path
                    } else {
                        (globalMappings[tempSourceType to tempTargetType] ?: mappingResolver(tempSourceType, tempTargetType))?.let { converter ->
                            sourceProp.path.readConverted(converter as Mapping<Any?, Any?>) to targetProp.path
                        }
                    }
                }
            }.toMap()
    }

    @ExperimentalStdlibApi
    @Suppress("UNCHECKED_CAST")
    fun buildConstructor(): ((S) -> T)? {
        return if (detectPrimaryConstructor) {
            val tempConstructor = targetClass.primaryConstructor ?: return null
            val tempParameters = tempConstructor.parameters.associateWith { parameter ->
                val tempTargetType = parameter.type
                val tempMappedProp = paramMatcher(parameter)
                val tempSourceType = tempMappedProp.returnType

                if (tempSourceType == tempTargetType) {
                    tempMappedProp.path
                } else {
                    (globalMappings[tempSourceType to tempTargetType] ?: mappingResolver(tempSourceType, tempTargetType))?.let { converter ->
                        tempMappedProp.readConverted(converter as Mapping<Any?, Any?>)
                    } ?: throw java.lang.IllegalArgumentException("Can not find matching property / matching mapping for parameter: ${parameter.name}")
                }
            }
            val tempConstructorDefinition: (S) -> T = { source -> tempConstructor.callBy(args = tempParameters.mapValues { it.value(source) }) as T }
            tempConstructorDefinition
        } else {
            null
        }
    }
}

internal class BasicDefaultBidirectionalMappingConfig<S, T>(private val sourceType: KType, private val targetType: KType) :
    DefaultBidirectionalMappingConfig<S, T> {

    private val excludedSourceProperties = mutableListOf<KProperty1<S, *>>()
    private val excludedTargetProperties = mutableListOf<KProperty1<T, *>>()

    private lateinit var sourceClass: KClass<*>
    private lateinit var targetClass: KClass<*>

    private lateinit var targetParamMatcher: (KParameter) -> KProperty1<S, *>
    private var detectSourcePrimaryConstructor = false

    private lateinit var sourceParamMatcher: (KParameter) -> KProperty1<T, *>
    private var detectTargetPrimaryConstructor = false

    //Lazy to be executed only after everything is configured!
    private val sourceProperties by lazy {
        sourceClass.memberProperties.filterIsInstance<KMutableProperty1<S, *>>().filterNotTo(arrayListOf(), excludedSourceProperties::contains)
    }

    //Lazy to be executed only after everything is configured!
    private val targetProperties by lazy {
        targetClass.memberProperties.filterIsInstance<KMutableProperty1<T, *>>().filterNotTo(arrayListOf(), excludedTargetProperties::contains)
    }

    private var sourcePropertyMatcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>? = { sourceProp -> targetProperties.find { it.name == sourceProp.name } }
    private var targetPropertyMatcher: (KProperty1<T, *>) -> KMutableProperty1<S, *>? = { sourceProp -> sourceProperties.find { it.name == sourceProp.name } }

    @ExperimentalStdlibApi
    private var mappingResolver: (KType, KType) -> Mapping<*, *>? = ::findMatchingMapping

    override fun KProperty1<S, *>.excludeFromSource() {
        excludedSourceProperties.add(this)
    }

    override fun KProperty1<T, *>.excludeFromTarget() {
        excludedTargetProperties.add(this)
    }

    override fun withPropertyMatcherToTarget(matcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>?) {
        sourcePropertyMatcher = matcher
    }

    override fun withPropertyMatcherToSource(matcher: (KProperty1<T, *>) -> KMutableProperty1<S, *>?) {
        targetPropertyMatcher = matcher
    }

    @ExperimentalStdlibApi
    override fun withMappingResolver(resolver: (KType, KType) -> Mapping<*, *>?) {
        mappingResolver = resolver
    }

    @Suppress("UNCHECKED_CAST")
    override fun detectTargetPrimaryConstructor() {
        detectTargetPrimaryConstructor { parameter ->
            sourceClass.memberProperties.find { it.name == parameter.name } as? KProperty1<S, *>
                ?: throw java.lang.IllegalArgumentException("Can not find matching property for parameter: ${parameter.name}")
        }
    }

    override fun detectTargetPrimaryConstructor(matcher: (KParameter) -> KProperty1<S, *>) {
        targetParamMatcher = matcher
        detectTargetPrimaryConstructor = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun detectSourcePrimaryConstructor() {
        detectSourcePrimaryConstructor { parameter ->
            targetClass.memberProperties.find { it.name == parameter.name } as? KProperty1<T, *>
                ?: throw java.lang.IllegalArgumentException("Can not find matching property for parameter: ${parameter.name}")
        }
    }

    override fun detectSourcePrimaryConstructor(matcher: (KParameter) -> KProperty1<T, *>) {
        sourceParamMatcher = matcher
        detectSourcePrimaryConstructor = true
    }

    @ExperimentalStdlibApi
    @Suppress("UNCHECKED_CAST")
    fun buildMappings(): Pair<Map<ReadPath<S, Any?>, ReadWritePath<T, Any?, Nothing>>, Map<ReadWritePath<S, Any?, Nothing>, ReadPath<T, Any?>>> {
        sourceClass = sourceType.classifier as? KClass<*> ?: throw IllegalArgumentException("Unable to perform default mapping!")
        targetClass = targetType.classifier as? KClass<*> ?: throw IllegalArgumentException("Unable to perform default mapping!")

        val tempTargetMappings = sourceClass.memberProperties.map { it as KProperty1<S, *> }
            .filterNot(excludedSourceProperties::contains)
            .mapNotNull { sourceProp ->
                val tempSourceType = sourceProp.returnType
                val tempTargetProp = sourcePropertyMatcher(sourceProp)

                tempTargetProp?.let { targetProp ->
                    val tempTargetType = targetProp.returnType
                    if (tempSourceType == tempTargetType) {
                        sourceProp.path to targetProp.path
                    } else {
                        (globalMappings[tempSourceType to tempTargetType] ?: mappingResolver(tempSourceType, tempTargetType))?.let { converter ->
                            sourceProp.path.readConverted { (converter as Mapping<Any?, Any?>).map(it) } to targetProp.path
                        }
                    }
                }
            }.toMap()

        val tempSourceMappings = targetClass.memberProperties.map { it as KProperty1<T, *> }
            .filterNot(excludedTargetProperties::contains)
            .mapNotNull { targetProp ->
                val tempTargetType = targetProp.returnType
                val tempSourceProp = targetPropertyMatcher(targetProp)

                tempSourceProp?.let { sourceProp ->
                    val tempSourceType = sourceProp.returnType
                    if (tempTargetType == tempSourceType) {
                        sourceProp.path to targetProp.path
                    } else {
                        globalMappings[tempTargetType to tempSourceType]?.let { converter ->
                            sourceProp.path to targetProp.path.readConverted { (converter as Mapping<Any?, Any?>).map(it) }
                        }
                    }
                }
            }.toMap()

        return Pair(tempTargetMappings, tempSourceMappings)
    }

    @ExperimentalStdlibApi
    fun buildSourceConstructor(): ((T) -> S)? {
        return if (detectSourcePrimaryConstructor) {
            val tempConstructor = sourceClass.primaryConstructor ?: return null
            val tempParameters = tempConstructor.parameters.associateWith { parameter ->
                val tempTargetType = parameter.type
                val tempMappedProp = sourceParamMatcher(parameter)
                val tempSourceType = tempMappedProp.returnType

                if (tempSourceType == tempTargetType) {
                    tempMappedProp.path
                } else {
                    (globalMappings[tempSourceType to tempTargetType] ?: mappingResolver(tempSourceType, tempTargetType))?.let { converter ->
                        tempMappedProp.readConverted(converter as Mapping<Any?, Any?>)
                    } ?: throw java.lang.IllegalArgumentException("Can not find matching property / matching mapping for parameter: ${parameter.name}")
                }
            }
            val tempConstructorDefinition: (T) -> S = { target -> tempConstructor.callBy(args = tempParameters.mapValues { it.value(target) }) as S }
            tempConstructorDefinition
        } else {
            null
        }
    }

    @ExperimentalStdlibApi
    fun buildTargetConstructor(): ((S) -> T)? {
        return if (detectTargetPrimaryConstructor) {
            val tempConstructor = targetClass.primaryConstructor ?: return null
            val tempParameters = tempConstructor.parameters.associateWith { parameter ->
                val tempTargetType = parameter.type
                val tempMappedProp = targetParamMatcher(parameter)
                val tempSourceType = tempMappedProp.returnType

                if (tempSourceType == tempTargetType) {
                    tempMappedProp.path
                } else {
                    (globalMappings[tempSourceType to tempTargetType] ?: mappingResolver(tempSourceType, tempTargetType))?.let { converter ->
                        tempMappedProp.readConverted(converter as Mapping<Any?, Any?>)
                    } ?: throw java.lang.IllegalArgumentException("Can not find matching property / matching mapping for parameter: ${parameter.name}")
                }
            }
            val tempConstructorDefinition: (S) -> T = { source -> tempConstructor.callBy(args = tempParameters.mapValues { it.value(source) }) as T }
            tempConstructorDefinition
        } else {
            null
        }
    }
}

@ExperimentalStdlibApi
private fun findMatchingMapping(sourceType: KType, targetType: KType): Mapping<*, *>? {
    return globalMappings.filter { (tempTypePair, _) ->
        sourceType.isSubtypeOf(tempTypePair.first) && targetType.isSupertypeOf(tempTypePair.second)
    }.map { it.value }.firstOrNull()
}