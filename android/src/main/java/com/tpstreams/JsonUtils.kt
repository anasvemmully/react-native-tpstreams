package com.tpstreams

import org.json.JSONObject

object JsonUtils {
    
    fun parseJsonString(map: Map<String, Any>?): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        map?.forEach { (key, value) ->
            when (value) {
                is String -> {
                    if (value.startsWith("{") && value.endsWith("}")) {
                        try {
                            val json = JSONObject(value)
                            result[key] = jsonToMap(json)
                        } catch (e: Exception) {
                            result[key] = value
                        }
                    } else {
                        result[key] = value
                    }
                }
                else -> result[key] = value
            }
        }
        return result
    }

    fun parseJsonObject(jsonString: String): Map<String, Any> {
        val json = JSONObject(jsonString)
        return jsonToMap(json)
    }

    private fun jsonToMap(json: JSONObject): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val keys = json.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = json.get(key)
            when (value) {
                is String -> map[key] = value
                is Int -> map[key] = value
                is Double -> map[key] = value
                is Boolean -> map[key] = value
                is JSONObject -> map[key] = jsonToMap(value)
                else -> map[key] = value.toString()
            }
        }
        return map
    }
} 