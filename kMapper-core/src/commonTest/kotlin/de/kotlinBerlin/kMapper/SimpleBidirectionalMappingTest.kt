package de.kotlinBerlin.kMapper

import de.kotlinBerlin.kMapper.extensions.*
import kotlin.properties.Delegates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimpleBidirectionalMappingTest {

    @ExperimentalStdlibApi
    @Test
    fun testSimpleMapping() {
        class Source(val firstName: String) {
            private lateinit var lastName: String

            fun getLastNameUi(): String = firstName
            fun setLastNameUi(lastName: String) {
                this.lastName = lastName
            }
        }

        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineBidirectionalMapping<Source, Target> {
            defSourceConstructor(::Source, Target::name)
            defTargetConstructor(::Target, Source::firstName)
            (Source::setLastNameUi + Source::getLastNameUi) map Target::name2
        }

        val source = Source("Vorname").apply {
            setLastNameUi("Nachname")
        }
        val tempMappedTarget = tempMapping.map(source)
        assertEquals(source.firstName, tempMappedTarget.name)
        assertEquals(source.getLastNameUi(), tempMappedTarget.name2)

        val tempMappedSource = tempMapping.mapReverse(tempMappedTarget)
        assertEquals(tempMappedTarget.name, tempMappedSource.firstName)
        assertEquals(tempMappedTarget.name2, tempMappedSource.getLastNameUi())
    }

    @ExperimentalStdlibApi
    @Test
    fun testListMappings() {
        class Names(val parts: MutableList<String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineBidirectionalMapping<Names, Target> {
            defSourceConstructor { Names(mutableListOf("", "")) }
            defTargetConstructor(::Target) { it.parts[0] }
            Names::parts[0] mapToSource Target::name
            Names::parts[1] map Target::name2
        }

        val tempNames = Names(mutableListOf("Vorname", "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts[0], tempMappedTarget.name)
        assertEquals(tempNames.parts[1], tempMappedTarget.name2)

        val tempMappedSource = tempMapping.mapReverse(tempMappedTarget)

        assertEquals(tempMappedTarget.name, tempMappedSource.parts[0])
        assertEquals(tempMappedTarget.name2, tempMappedSource.parts[1])
    }

    @ExperimentalStdlibApi
    @Test
    fun testArrayMappings() {
        class Names(val parts: Array<String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineBidirectionalMapping<Names, Target> {
            defSourceConstructor { Names(arrayOf("", "")) }
            defTargetConstructor(::Target) { it.parts[0] }
            Names::parts[0] mapToSource Target::name
            Names::parts[1] map Target::name2
        }

        val tempNames = Names(arrayOf("Vorname", "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts[0], tempMappedTarget.name)
        assertEquals(tempNames.parts[1], tempMappedTarget.name2)

        val tempMappedSource = tempMapping.mapReverse(tempMappedTarget)

        assertEquals(tempMappedTarget.name, tempMappedSource.parts[0])
        assertEquals(tempMappedTarget.name2, tempMappedSource.parts[1])
    }

    @ExperimentalStdlibApi
    @Test
    fun testMapMappings() {
        class Names(val parts: MutableMap<String, String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineBidirectionalMapping<Names, Target> {
            defSourceConstructor { Names(mutableMapOf()) }
            defTargetConstructor(::Target) { it.parts["firstName"]!! }
            Names::parts["firstName"] mapToSource Target::name
            Names::parts["secondName"].readNotNull { throw IllegalStateException() } map Target::name2
        }

        val tempNames = Names(hashMapOf("firstName" to "Vorname", "secondName" to "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts["firstName"], tempMappedTarget.name)
        assertEquals(tempNames.parts["secondName"], tempMappedTarget.name2)

        val tempMappedSource = tempMapping.mapReverse(tempMappedTarget)

        assertEquals(tempMappedTarget.name, tempMappedSource.parts["firstName"])
        assertEquals(tempMappedTarget.name2, tempMappedSource.parts["secondName"])
    }

    @ExperimentalStdlibApi
    @Test
    fun testNestedMappings() {
        val tempListMapping = defineBidirectionalMapping<List<String>, List<Int>> {
            defSourceConstructor { it.map { it.toString() } }
            defTargetConstructor { it.mapTo(arrayListOf()) { it.toInt() } }
        }

        class Sub {
            lateinit var name: String
        }

        class Source {
            var age by Delegates.notNull<Int>()
            lateinit var list: List<String>
            lateinit var name2: String
            val sub: Sub = Sub()
        }

        class Target {
            lateinit var age: String
            lateinit var list: List<Int>
            lateinit var name2: String
            val sub: Sub = Sub()
        }

        val tempMapping = defineBidirectionalMapping<Source, Target> {
            defTargetConstructor(::Target)
            defSourceConstructor(::Source)
            Source::sub[Sub::name] map (Target::sub[Sub::name])
            Source::name2 map Target::name2
            Source::age.readAsString() map Target::age.readAsInt()
            Source::list.converted(tempListMapping) map Target::list
        }

        val tempSource = Source()
        tempSource.sub.name = "Vorname"
        tempSource.name2 = "Nachname"
        tempSource.age = 10
        tempSource.list = arrayListOf("1", "2")

        val tempMappedTarget = tempMapping.map(tempSource)

        assertEquals(tempSource.sub.name, tempMappedTarget.sub.name)
        assertEquals(tempSource.name2, tempMappedTarget.name2)
        assertEquals(tempSource.age.toString(), tempMappedTarget.age)
        assertEquals(2, tempMappedTarget.list.size)
        assertTrue { tempMappedTarget.list.contains(1) }
        assertTrue { tempMappedTarget.list.contains(2) }

        val tempMappedSource = tempMapping.mapReverse(tempMappedTarget)

        assertEquals(tempMappedTarget.sub.name, tempMappedSource.sub.name)
        assertEquals(tempMappedTarget.name2, tempMappedSource.name2)
        assertEquals(tempMappedTarget.age.toInt(), tempMappedSource.age)
        assertEquals(2, tempMappedSource.list.size)
        assertTrue { tempMappedSource.list.contains("1") }
        assertTrue { tempMappedSource.list.contains("2") }

    }
}