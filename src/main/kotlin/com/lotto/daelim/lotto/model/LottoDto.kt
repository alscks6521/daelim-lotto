package com.lotto.daelim.lotto.model

data class LottoDto(
    val numbers: List<Int>
)

data class LottoResult(
    val numbers: List<Int>,
    val correctNumbers: LottoResultRequestDto,
    val result: String
)

data class LottoResultRequestDto(
    val numbers: List<Int>,
    val bonusNumber: Int?
)

data class LottoResultResponseDto(
    val index: Int,
    val winningNumbers: LottoResultRequestDto,
    val results: List<String>
)
