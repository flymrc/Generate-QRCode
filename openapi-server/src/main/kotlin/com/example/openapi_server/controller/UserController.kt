package com.example.openapi_server.controller

import com.example.openapi_server.api.UserApi
import com.example.openapi_server.model.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class UserController : UserApi {
    override fun userCurrentGet(): ResponseEntity<User> {
        return ResponseEntity.ok().body(
            User(
                userId = "1",
                nickname = "abc"
            )
        )
    }
}
