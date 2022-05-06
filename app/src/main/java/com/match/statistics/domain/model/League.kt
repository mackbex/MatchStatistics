package com.match.statistics.domain.model

data class League(
    val wins:Int,
    val losses:Int,
    val winRate:Int,
    val tierRank:TierRank

)
