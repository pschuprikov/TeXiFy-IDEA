package nl.rubensten.texifyidea.lang.magic

/**
 * A magic comment is a special comment that provides meta data about the document to the IDE.
 *
 * They store values for each key defined in a magic comment.
 * A single key can have multiple values: when the same key is used multiple times.
 * The values will be stored in order.
 * When the key has no value (i.e. the key is mentioned standalone in a comment without an assignment)
 * the list will be empty.
 * When a key is not present in the magic comment, the store does not contain the key.
 *
 * Note that keys are unique, values are not.
 *
 * @param Key
 *          The type of key to use.
 * @param Value
 *          The type of the values.
 *
 * @author Ruben Schellekens
 */
abstract class MagicComment<Key, Value> {

    /**
     * Maps all keys in the magic comment to their values.
     *
     * @see [MagicComment]
     */
    protected val keyValueStore: MutableMap<Key, MutableList<Value>> = HashMap()

    /**
     * Checks if a key-value pair with the given key is present in the magic comment.
     *
     * @return `true` if present, `false` if absent.
     */
    fun containsKey(key: MagicKey<Key>): Boolean = keyValueStore.containsKey(key.key)

    /**
     * Get the value of the first key-value pair with the given key.
     *
     * @return The value of the first key-value pair, or `null` when the key is absent, or when there are no values
     * available for the given key.
     */
    fun value(key: MagicKey<Key>): Value? = keyValueStore[key.key]?.firstOrNull()

    /**
     * Get all the values of the key-value pairs with the given key.
     *
     * @return The list of all stored values for the given key (empty list if the key is registered without a value),
     * or `null` when the key is absent.
     */
    fun values(key: MagicKey<Key>): List<Value>? = keyValueStore[key.key]

    /**
     * The total number of keys in the magic comment.
     */
    fun size() = keyValueStore.size

    /**
     * @see [containsKey]
     */
    operator fun contains(key: MagicKey<Key>) = containsKey(key)

    override fun toString() = keyValueStore.toString()
}

/**
 * See [MagicComment], but mutable.
 *
 * @author Ruben Schellekens
 */
open class MutableMagicComment<Key, Value> : MagicComment<Key, Value>() {

    /**
     * Adds a new key-value pair to the magic comment.
     *
     * If the key already exists, the value will be added to the pool of values for the given key.
     * When the value is `null`, no value is added, but the key is.
     */
    fun addValue(key: MagicKey<Key>, value: Value?) {
        val list = keyValueStore[key.key] ?: ArrayList()
        value?.let { list.add(it) }
        keyValueStore[key.key] = list
    }

    /**
     * Removes all key-value pairs with the given key from the comment.
     *
     * @return The previous value associated with the key, or `null` if the key was not present in the map.
     */
    fun removeKey(key: MagicKey<Key>): List<Value>? = keyValueStore.remove(key.key)

    /**
     * Removes all key-value pairs from the magic comment.
     */
    fun clear() = keyValueStore.clear()
}