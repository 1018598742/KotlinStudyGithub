package com.fta.testdemo.utils

fun toMap(any: Any): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val javaClass = any.javaClass
    val declaredFields = javaClass.declaredFields
    declaredFields.forEach {
        val name = it.name
        it.isAccessible = true
        val value: Any? = when (it.type) {
            String::class.java -> it.get(any) as? String
            Int::class.java -> it.getInt(any)
            Long::class.java -> it.getLong(any)
            else -> null
        }
        value?.let {
            map.put(name, it)
        }
    }
    return map
}

fun toStringMap(any: Any): Map<String, String> {
    val toMap = toMap(any)
    val map = mutableMapOf<String, String>()
    toMap.forEach {
        map[it.key] = it.value.toString()
    }
    return map

}