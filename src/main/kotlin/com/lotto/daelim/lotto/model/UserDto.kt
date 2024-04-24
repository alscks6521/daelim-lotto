package com.lotto.daelim.lotto.model

data class LoginRequest(
    val email: String,
    val password: String
)
data class CreateUserRequest(
    val fullName: String,
    val email: String,
    val password: String
)
