package com.lotto.daelim.lotto.model

data class UserRequestDto(
    val email: String,
    val password: String
)

data class UserDto(
    val fullName: String,
    val email: String,
    val password: String
)