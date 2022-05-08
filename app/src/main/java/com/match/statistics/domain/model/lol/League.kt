package com.match.statistics.domain.model.lol

data class League(
    val wins:Int,
    val losses:Int,
    var winRate:Int,
    val rankType:String,
    val tier:String,
    val tierImage:String,
    val lp:Int
)