package com.example.openapi_server.api

import cn.leancloud.AVException
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

// TODO Extend ApiException for custom exception handling, e.g. the below NotFound exception
sealed class ApiException(msg: String, val code: Int) : Exception(msg)

class NotFoundException(msg: String, code: Int = HttpStatus.NOT_FOUND.value()) : ApiException(msg, code)

@ControllerAdvice
class DefaultExceptionHandler {
    private val logger = LogManager.getLogger(DefaultExceptionHandler::class.java)

    @ExceptionHandler(value = [ApiException::class])
    fun onApiException(ex: ApiException, response: HttpServletResponse): Unit =
        response.sendError(ex.code, ex.message)

    @ExceptionHandler(value = [NotImplementedError::class])
    fun onNotImplemented(ex: NotImplementedError, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.NOT_IMPLEMENTED.value())

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun onConstraintViolation(ex: ConstraintViolationException, response: HttpServletResponse): Unit =
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.constraintViolations.joinToString(", ") { it.message })

    @ExceptionHandler(value = [AVException::class])
    fun onAVException(ex: AVException, response: HttpServletResponse) {
        when (ex.code) {
            AVException.OBJECT_NOT_FOUND -> {
                response.sendError(HttpStatus.NOT_FOUND.value(), ex.message)
            }
            else -> {
                logger.warn("Unknown AVException", ex)
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
            }
        }
    }
}
