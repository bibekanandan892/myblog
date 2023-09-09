package com.popshop.myblog.api


import com.popshop.myblog.data.MongoDB
import com.popshop.myblog.models.User
import com.popshop.myblog.models.UserWithoutPassword
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        val userRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        val user = userRequest?.let {
            context.logger.debug("CURRENT_USER")
            context.logger.debug(hashPassword(it.username))
            context.logger.debug(hashPassword(it.password))
            context.data.getValue<MongoDB>().checkUserExistence(
                User(username = it.username, password = hashPassword(it.password))
            )
        }
        if (user != null) {
            context.logger.debug("CURRENT_USER")
            context.logger.debug("USER NOT NULL")
            context.res.setBodyText(
                Json.encodeToString(
                    UserWithoutPassword(id = user.id, username = user.username)
                )
            )
        } else {
            context.logger.debug("CURRENT_USER")
            context.logger.debug("USER NULL")
            context.res.setBodyText(Json.encodeToString(Exception("User doesn't exist.")))
        }
    } catch (e: Exception) {
        context.logger.debug("CURRENT_USER")
        context.logger.debug(e.message.toString())
        context.res.setBodyText(Json.encodeToString(Exception(e.message)))
    }
}

@Api(routeOverride = "checkuserid")
suspend fun checkUserId(context: ApiContext) {
    try {
        val idRequest =
            context.req.body?.decodeToString()?.let { Json.decodeFromString<String>(it) }
        val result = idRequest?.let {
            context.data.getValue<MongoDB>().checkUserId(it)
        }
        if (result != null) {
            context.res.setBodyText(Json.encodeToString(result))
        } else {
            context.res.setBodyText(Json.encodeToString(false))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(false))
    }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}