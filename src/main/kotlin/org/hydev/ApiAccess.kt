package org.hydev

import org.eclipse.jetty.server.Request
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * An ApiAccess instance contains the data that the client sent to the server.
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2020-07-03 12:37
 */
class ApiAccess(
    path: String,
    baseRequest: Request,
    request: HttpServletRequest,
    response: HttpServletResponse,
    val node: ApiNode,
    val body: String
): HttpAccess(path, baseRequest, request, response)
{
    val headers = request.mapHeaders()

    /**
     * This is called when an error occurs
     *
     * @return String
     */
    override fun toString(): String
    {
        return """Access: ${request.method} $path
            |- With headers: $headers
            |- With body: $body""".trimMargin()
    }
}
