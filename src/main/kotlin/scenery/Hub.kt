package scenery

import java.util.concurrent.ConcurrentHashMap

/**
 * The Hub class interconnects the different components a scenery application may
 * have, such as a renderer or compute context (see also [SceneryElement].
 *
 * @author Ulrik Günther <hello@ulrik.is>
 */
class Hub {
    /** Hash map storage for all the [SceneryElements] and their instances */
    val elements: ConcurrentHashMap<SceneryElement, Any> = ConcurrentHashMap()

    /**
     * Adds a given [SceneryElement] to the Hub.
     *
     * @param[type] The type of [SceneryElement] to add.
     * @param[obj] The [Hubable] object.
     */
    fun add(type: SceneryElement, obj: Hubable) {
        elements.put(type, obj)

        obj.hub = this
    }

    /**
     * Query the Hub for a given type of [SceneryElement]
     *
     * @param[type] [SceneryElement] type.
     * @return The instance of [SceneryElement] currently registered.
     */
    fun get(type: SceneryElement): Any? {
        return elements.get(type)
    }

    /**
     * Check whether the Hub has this type of [SceneryElement] registered
     *
     * @param[type] [SceneryElement] type to query for.
     * @return True if [type] is registered, else false.
     */
    fun has(type: SceneryElement): Boolean {
        return elements.containsKey(type)
    }
}
