package com.match.statistics.domain.model.lol

data class Summoner(
    val name:String,
    val profileImageUrl:String,
    val level:Int,
    val leagues:List<League>
)