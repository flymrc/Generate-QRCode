package com.example.openapi_server.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

/**
 *
 * @param objectId
 * @param userId
 * @param nickname
 * @param avatarUrl
 */
data class XUser(

    @get:NotNull
    @field:JsonProperty("objectId") val objectId: kotlin.String,

    @get:NotNull
    @field:JsonProperty("userId") val userId: kotlin.String,

    @get:NotNull
    @field:JsonProperty("nickname") val nickname: kotlin.String,

    @get:NotNull
    @field:JsonProperty("avatarUrl") val avatarUrl: kotlin.String
)
