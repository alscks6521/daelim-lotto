package com.lotto.daelim.lotto.main
import com.lotto.daelim.lotto.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    private val users = mutableListOf<UserDto>()

    @GetMapping("/")
    fun getAllPosts(): ResponseEntity<List<UserDto>>{
        return ResponseEntity.ok().body(users)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: UserRequestDto
    ): ResponseEntity<String> {
        // 로그인 로직은 이전과 동일
        val user = users.find { it.email == loginRequest.email }

        // 로그인 검사 체크!
        return if (user != null && user.password == loginRequest.password) {
            ResponseEntity.ok("로그인 성공, ${user.fullName}")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 올바르지 않습니다.")
        }
    }

    @PostMapping("/create")
    fun createUser(
        @RequestBody userDto: UserDto): ResponseEntity<Any> {
        // 이메일 중복 검사
        if (users.any { it.email == userDto.email }) {
            return ResponseEntity.badRequest().body("Please enter valid email address.")
        }
        users.add(userDto)
        return ResponseEntity.ok().body(userDto)
    }
}


@RestController
@RequestMapping("/lotto")
class LottoController {

    private val lottos = mutableListOf<LottoDto>()
    private val lottoResults = mutableListOf<LottoResult>()
    private var lottoIndex = 1

    // 랜덤생성 로또 ------------------------------------------------------
    @GetMapping("/")
    fun getLottoNumbers(): ResponseEntity<List<LottoDto>> {
        val lottoNumberSets = (1..5).map {
            LottoDto((1..45).shuffled().take(7).sorted())
        }
        // 생성된 번호들을 임시 저장소 추가.
        lottos.addAll(lottoNumberSets)
        return ResponseEntity.ok().body(lottoNumberSets)
    }

    // 결과 체크 ------------------------------------------------------
    @PostMapping("/")
    fun postLottoNumbers(
        @RequestBody lottoRequest: LottoResultRequestDto
    ): ResponseEntity<LottoResultResponseDto> {
        val winningNumbers = listOf(15, 16, 17, 25, 30, 31)
        val bonusNumber = 32

        // 필터링
        val correctNumbers = lottoRequest.numbers.filter { it in winningNumbers }
        val hasBonusNumber = lottoRequest.bonusNumber == bonusNumber

        val result = when {
            correctNumbers.size == 6 -> "1등"
            correctNumbers.size == 5 && hasBonusNumber -> "2등"
            correctNumbers.size == 5 -> "3등"
            correctNumbers.size == 4 -> "4등"
            correctNumbers.size == 3 -> "5등"
            else -> "낙첨"
        }

        val lottoResult = LottoResult(
            numbers = lottoRequest.numbers,
            correctNumbers = LottoResultRequestDto(correctNumbers, lottoRequest.bonusNumber),
            result = result
        )
        lottoResults.add(lottoResult)

        val responseDto = LottoResultResponseDto(
            index = lottoIndex++,
            winningNumbers = LottoResultRequestDto(winningNumbers, bonusNumber),
            results = listOf(result)
        )
        return ResponseEntity.ok().body(responseDto)
    }

    // 현재까지의 당첨 결과 반환 ------------------------------------------------------
    @GetMapping("/check")
    fun checkLottoResult(): ResponseEntity<List<LottoResult>> {
        return ResponseEntity.ok().body(lottoResults)
    }


}
