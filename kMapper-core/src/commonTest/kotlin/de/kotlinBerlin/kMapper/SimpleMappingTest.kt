package de.kotlinBerlin.kMapper

import de.kotlinBerlin.kMapper.extensions.*
import kotlin.properties.Delegates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimpleMappingTest {

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

        val tempMapping = defineMapping<Source, Target> {
            defConstructor(::Target, Source::firstName)
            Source::getLastNameUi map Target::name2
        }

        val source = Source("Vorname").apply {
            setLastNameUi("Nachname")
        }
        val tempMappedTarget = tempMapping.map(source)
        assertEquals(source.firstName, tempMappedTarget.name)
        assertEquals(source.getLastNameUi(), tempMappedTarget.name2)
    }

    @ExperimentalStdlibApi
    @Test
    fun testListMappings() {
        class Names(val parts: MutableList<String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineMapping<Names, Target> {
            defConstructor(::Target) { it.parts[0] }
            Names::parts[1] map Target::name2
        }

        val tempNames = Names(mutableListOf("Vorname", "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts[0], tempMappedTarget.name)
        assertEquals(tempNames.parts[1], tempMappedTarget.name2)
    }

    @ExperimentalStdlibApi
    @Test
    fun testArrayMappings() {
        class Names(val parts: Array<String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineMapping<Names, Target> {
            defConstructor(::Target) { it.parts[0] }
            Names::parts[1] map Target::name2
        }

        val tempNames = Names(arrayOf("Vorname", "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts[0], tempMappedTarget.name)
        assertEquals(tempNames.parts[1], tempMappedTarget.name2)
    }

    @ExperimentalStdlibApi
    @Test
    fun testMapMappings() {
        class Names(val parts: MutableMap<String, String>)
        class Target(val name: String) {
            lateinit var name2: String
        }

        val tempMapping = defineMapping<Names, Target> {
            defConstructor(::Target) { it.parts["firstName"]!! }
            Names::parts["secondName"].readNotNull { throw IllegalStateException() } map Target::name2
        }

        val tempNames = Names(hashMapOf("firstName" to "Vorname", "secondName" to "Nachname"))
        val tempMappedTarget = tempMapping.map(tempNames)

        assertEquals(tempNames.parts["firstName"], tempMappedTarget.name)
        assertEquals(tempNames.parts["secondName"], tempMappedTarget.name2)
    }

    @ExperimentalStdlibApi
    @Test
    fun testNestedMappings() {
        val tempListMapping = defineBidirectionalMapping<List<String>, List<Int>> {
            defSourceConstructor { it.map { it.toString() } }
            defTargetConstructor { it.map { it.toInt() } }
        }

        class Sub {
            lateinit var name: String
        }

        class Source {
            var age by Delegates.notNull<Int>()
            lateinit var list: ArrayList<String>
            lateinit var name2: String
            val sub: Sub = Sub()
        }

        class Target {
            lateinit var age: String
            lateinit var list: List<Int>
            lateinit var name2: String
            var sub: Sub? = null
        }

        val tempMapping = defineMapping<Source, Target> {
            defConstructor(::Target)

            Source::sub[Sub::name] map (Target::sub.initOnRead { Sub() }[Sub::name])
            Source::name2 map Target::name2
            Source::age.readAsString() map Target::age.readAsInt()
            Source::list.readConverted(tempListMapping) map Target::list
        }

        val tempSource = Source()
        tempSource.sub.name = "Vorname"
        tempSource.name2 = "Nachname"
        tempSource.age = 10
        tempSource.list = arrayListOf("1", "2")

        val tempMappedTarget = tempMapping.map(tempSource)

        assertEquals(tempSource.sub.name, tempMappedTarget.sub?.name)
        assertEquals(tempSource.name2, tempMappedTarget.name2)
        assertEquals(tempSource.age.toString(), tempMappedTarget.age)
        assertEquals(2, tempMappedTarget.list.size)
        assertTrue { tempMappedTarget.list.contains(1) }
        assertTrue { tempMappedTarget.list.contains(2) }
    }
}