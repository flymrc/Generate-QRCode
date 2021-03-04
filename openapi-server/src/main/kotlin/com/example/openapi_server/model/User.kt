package com.example.openapi_server.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * 
 * @param userId 
 * @param nickname 
 * @param objectId 
 */
data class User(

    @get:NotNull  
    @field:JsonProperty("userId") val userId: kotlin.String,

    @get:NotNull  
    @field:JsonProperty("nickname") val nickname: kotlin.String,

    @field:JsonProperty("objectId") val objectId: kotlin.String? = null
) {

}

