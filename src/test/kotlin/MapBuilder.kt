class MapBuilder(private var map: MutableMap<String, Any> = mutableMapOf()) {
    fun put(keys: String, value: String): MapBuilder {
        val keysArray = keys.split(".")
        put(map, keysArray, value)
        return this
    }

    private fun put(map: MutableMap<String, Any>, keys: List<String>, value: String) {
        val key = keys[0]
        val next = map[key] as MutableMap<String, Any>? ?: mutableMapOf<String, Any>()
        if (keys.size == 1) {
            map[key] = value
        } else {
            map[key] = next
            put(next, keys.subList(1, keys.size), value)
        }
    }

    fun build(): MutableMap<String, Any> {
        return map
    }
}