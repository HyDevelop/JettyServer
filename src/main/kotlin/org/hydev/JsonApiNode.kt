package org.hydev

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer
import kotlinx.serialization.stringify
import kotlin.reflect.KClass

/**
 * API Node that processes Json.
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-06-21 15:19
 */
abstract class JsonApiNode<T: Any>(
    path: String,
    val modelClass: KClass<T>,
    isSecret: Boolean = false,
    val maxLength: Int = 10000,
    val parser: Json = Json(JsonConfiguration.Stable)): ApiNode(path, isSecret)
{
    /**
     * Process http node access to check for stuff and parse json.
     *
     * @param access Http node access
     * @return Data sent back to the user
     */
    @ImplicitReflectionSerializer
    override fun process(access: ApiAccess): Any
    {
        // Validate body
        if (access.body.isEmpty()) throw KnownException(jsonError("Request body is empty."))
        if (access.body.length > maxLength) throw KnownException(jsonError("Body too long."))

        // Parse body as json
        val data = parser.parse(modelClass.serializer(), access.body)

        // Get and return processed results
        val result = json(access, data)
        return if (result is String) result else parser.stringify(result)
    }

    abstract fun json(access: ApiAccess, data: T): Any
}
