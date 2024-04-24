package com.lotto.daelim.lotto.main

import com.lotto.daelim.lotto.model.CreateUserRequest
import com.lotto.daelim.lotto.model.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping("/user")
class UserController {

    @PostMapping("/{login}")
    fun login(
        @RequestBody loginRequest: LoginRequest,
    ): String {
        // 로그인 처리 로직 구현
        return "User logged in"
    }

    // 글 생성----------------------------------------------------------------------------
    @PostMapping("/")
    fun createPost(
        @RequestBody postDtoRequest: PostDtoRequest
    ): ResponseEntity<PostDto> {
        val post = PostDto(
//            id = index++,
            id = Random.nextInt(10000),
            title = postDtoRequest.title,
            content = postDtoRequest.content
        )
        posts.add(post)

        return ResponseEntity.ok().body(post)
    }

    @PostMapping("/create")
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): String {
        // 사용자 생성 로직 구현
        return "User created"
    }
}

@RestController
@RequestMapping("/lotto")
class LottoController {

    @GetMapping
    fun getLotto(): List<Int> {
        // 로또 번호 가져오기 로직 구현
        return listOf(1, 2, 3, 4, 5, 6) // 예시 번호
    }

    @PostMapping
    fun postLotto(@RequestBody lottoNumbers: List<Int>): String {
        // 로또 번호 제출 로직 구현
        return "Lotto numbers submitted"
    }

    @GetMapping("/check")
    fun checkLotto(): String {
        // 당첨 번호 확인 로직 구현
        return "Lotto results checked"
    }
}
