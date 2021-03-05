package com.example.openapi_server.auth

import cn.leancloud.AVRole
import cn.leancloud.AVUser
import com.google.common.base.CaseFormat
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class Authentication(private val user: AVUser?) :
    Authentication {
    private var authenticated = true

    override fun getName(): String {
        return user?.objectId ?: ANONYMOUS_NAME
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return (
            user?.rolesInBackground?.blockingLast()?.map {
                LCRole(it)
            } ?: emptyList()
            ).toMutableList<GrantedAuthority>()
    }

    override fun getCredentials(): Any? {
        return user?.sessionToken
    }

    override fun getDetails(): Any? {
        return user
    }

    override fun getPrincipal(): Any? {
        return user
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }

    private class LCRole(private val role: AVRole) : GrantedAuthority {
        override fun getAuthority(): String = "ROLE_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, role.name)
    }

    companion object {
        private const val ANONYMOUS_NAME = "anonymous"
    }
}
