package com.match.statistics.domain.model.lol

data class SummonerInfo(
    val summoner: Summoner,
    val analysis: Analysis? = null
)