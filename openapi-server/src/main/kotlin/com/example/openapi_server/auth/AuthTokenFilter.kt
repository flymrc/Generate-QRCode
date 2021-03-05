package com.example.openapi_server.auth


import org.eclipse.jetty.http.HttpTokens
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder


import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthTokenFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val auth = Authentication(null)
        UsernamePasswordAuthenticationToken("", null)
        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }
}