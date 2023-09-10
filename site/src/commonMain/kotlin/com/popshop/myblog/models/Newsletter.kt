package com.popshop.myblog.models

import kotlinx.serialization.Serializable

@Serializable
data class Newsletter(
    val email: String
)