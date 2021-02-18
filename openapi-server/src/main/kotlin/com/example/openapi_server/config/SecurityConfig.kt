package com.example.openapi_server.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.AccessDeniedHandlerImpl

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter(true) {

    override fun configure(http: HttpSecurity) {
        http.cors()
            .and().csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()

            // Allow access from local.
            .authorizeRequests()
            .requestMatchers(EndpointRequest.toAnyEndpoint())
            .hasIpAddress("127.0.0.1")

            // Allow other access.
            .and().authorizeRequests()
            .anyRequest()
            .authenticated()

            .and().logout().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
    }

    fun accessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandlerImpl()
    }
}
