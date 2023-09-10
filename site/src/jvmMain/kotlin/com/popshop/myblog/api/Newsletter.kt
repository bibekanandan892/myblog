package com.popshop.myblog.api

import com.popshop.myblog.data.MongoDB
import com.popshop.myblog.models.Newsletter
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "subscribe")
suspend fun subscribeNewsletter(context: ApiContext) {
    try {
        val newsletter = context.req.getBody<Newsletter>()
        context.res.setBody(newsletter?.let {
            context.data.getValue<MongoDB>().subscribe(newsletter = it)
        })
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}