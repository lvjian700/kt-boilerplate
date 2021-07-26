package com.kt.app

import java.lang.RuntimeException

data class NotFoundException(override val message: String) : RuntimeException(message)
